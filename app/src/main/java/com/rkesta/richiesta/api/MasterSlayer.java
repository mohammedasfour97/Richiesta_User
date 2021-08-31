package com.rkesta.richiesta.api;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.rkesta.richiesta.util.utility.substringAfter;

public class MasterSlayer {

//    public var Url:String = "http://delivery.rkesta.com/Service.asmx"
//    public var UploadUrl:String = "http://cp.rkesta.com/sprj/APIAdditional.asmx"
//    public var Host:String = "cp.rkesta.com"
//
//    public var imageURL = "http://cp.rkesta.com/prdPic/"
//    public var imageURL_NoImage = "http://cp.rkesta.com/prdPic/ProdNotAvailable.png"
//    public var imageURL_UserProfile = "http://cp.rkesta.com/images/usrprofile.png"

    private String NAMESPACE = "http://tempuri.org/";
    public String URL = "http://cvr.rkesta.info/UserAPI.asmx";///MyService
    private String SOAP_ACTION = "http://tempuri.org/";
    private String ImageURl = "http://cp.rkesta.info";
    private String UniqeImageURl = "http://rkesta.com/images/";

    private String NoImg = "ProdNotAvailable.png";


    public String FunctionName = "";

    //send param
    private ArrayList<String> ParamSendName = new ArrayList<String>();
    private ArrayList<String> ParamSendValue = new ArrayList<String>();

    //request param
    private ArrayList<String> request_paramName = new ArrayList<String>();


    public MasterSlayer(String functionName) {
        FunctionName = functionName;
    }



    public void addsendparam(ArrayList<String> param, ArrayList<String> value) {
        if (param.size() != value.size()) {
            return;
        } else {
            ParamSendName.addAll(param);

            ParamSendValue.addAll(value);
        }
    }

    public void setRequest_paramName(ArrayList<String> request_paramName) {
        this.request_paramName = request_paramName;
    }

    public ArrayList<HashMap<String, String>> Call() {
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, FunctionName);
        /**
         ParamSendName = ["x","y","z"]
         ParamSendValue = ["xvalue","yvalue","zvalue"]
         */
        for (int i = 0; i < ParamSendName.size(); i++) {
            //request.addProperty(getPI("x","xvalue"), String.class));
            request.addProperty(getPI(ParamSendName.get(i), ParamSendValue.get(i), String.class));
        }
//		request.addProperty(getPI("UserName",UserName, String.class));
        /** end send*/
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        ArrayList<HashMap<String, String>> ArrayListHash = null;
        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + FunctionName, envelope);
            // Get the response

            SoapObject resultsString = (SoapObject) envelope.getResponse();
            //String response = resultsString.getProperty(0).toString();
            SoapObject diffgram = (SoapObject) resultsString.getProperty(1);
            SoapObject DocumentElement = (SoapObject) diffgram.getProperty(0);
            ArrayListHash = null;
/**
 ==============================
 * */
            ArrayListHash = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < DocumentElement.getPropertyCount(); i++) {
                SoapObject Objecttable = (SoapObject) DocumentElement.getProperty(i);
                HashMap<String, String> map = new HashMap<String, String>();
                for (int name_num = 0; name_num < request_paramName.size(); name_num++) {

                    if (Objecttable.hasProperty(request_paramName.get(name_num))) {

                        /**map.put("ID", Objecttable.getProperty("ID").toString());*/
                        if (IsImage(request_paramName.get(name_num))) { /** Is Image );*/

                            if (Objecttable.getProperty(request_paramName.get(name_num)).toString().equals("anyType{}")) {
                                map.put(request_paramName.get(name_num), ImageURl + NoImg);
                            } else {
                                map.put(request_paramName.get(name_num), ImageURl + (Objecttable.getProperty(request_paramName.get(name_num)).toString().substring(5)));
                            }
                        } else {
                            if (Objecttable.getProperty(request_paramName.get(name_num)).toString().equals("anyType{}")) {
                                map.put(request_paramName.get(name_num), "");
                            } else {
                                map.put(request_paramName.get(name_num), Objecttable.getProperty(request_paramName.get(name_num)).toString());
                            }
                        }

                    } else {
                        map.put(request_paramName.get(name_num), "");
                    }
                }
//				if(Objecttable.hasProperty("Code")) {
//					map.put("Code", Objecttable.getProperty("Code").toString());
//				}else{
//					map.put("Code", "");
//				}
                ArrayListHash.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("popopo", "Call: ", e);
            ArrayListHash = new ArrayList<HashMap<String, String>>();
        }

        return ArrayListHash;
    }

    public HashMap<String, ArrayList<HashMap<String, String>>> CallMulty(HashMap<String , ArrayList<String>> requestes_tables) {
        HashMap<String, ArrayList<HashMap<String, String>>> Response_arrayHashMap = new HashMap<String, ArrayList<HashMap<String, String>>>();

        for (String key : requestes_tables.keySet()) {
            // ... add every table name to return
            Response_arrayHashMap.put(key,new ArrayList<HashMap<String, String>>());
        }

        // Create request
        SoapObject request = new SoapObject(NAMESPACE, FunctionName);
        /**
         ParamSendName = ["x","y","z"]
         ParamSendValue = ["xvalue","yvalue","zvalue"]
         */
        for (int i = 0; i < ParamSendName.size(); i++) {
            //request.addProperty(getPI("x","xvalue"), String.class));
            request.addProperty(getPI(ParamSendName.get(i), ParamSendValue.get(i), String.class));
        }
//		request.addProperty(getPI("UserName",UserName, String.class));
        /** end send*/
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + FunctionName, envelope);
            // Get the response

            SoapObject resultsString = (SoapObject) envelope.getResponse();
            //String response = resultsString.getProperty(0).toString();
            SoapObject diffgram = (SoapObject) resultsString.getProperty(1);
            SoapObject DocumentElement = (SoapObject) diffgram.getProperty(0);

/**
 ==============================
 * */

            for (int i = 0; i < DocumentElement.getPropertyCount(); i++) {
                SoapObject Objecttable = (SoapObject) DocumentElement.getProperty(i);
                HashMap<String, String> map = new HashMap<String, String>();

                try{

                    for (String key : requestes_tables.keySet()) {
                        // ...
//                        Response_arrayHashMap.put(key,new ArrayList<HashMap<String, String>>());

                        if(Objecttable.getAttribute("id").equals(key+(Integer.parseInt(Objecttable.getAttribute("rowOrder").toString())+1))){

                            for (int name_num = 0; name_num < requestes_tables.get(key).size(); name_num++) {

                                if (Objecttable.hasProperty(requestes_tables.get(key).get(name_num))) {

                                    /**map.put("ID", Objecttable.getProperty("ID").toString());*/
                                    if (IsImage(requestes_tables.get(key).get(name_num))) { /** Is Image );*/

                                        if (Objecttable.getProperty(requestes_tables.get(key).get(name_num)).toString().equals("anyType{}")) {
                                            map.put(requestes_tables.get(key).get(name_num), ImageURl + NoImg);
                                        } else {
                                            map.put(requestes_tables.get(key).get(name_num), ImageURl + (Objecttable.getProperty(requestes_tables.get(key).get(name_num)).toString().substring(5)));
                                        }
                                    } else {
                                        if (Objecttable.getProperty(requestes_tables.get(key).get(name_num)).toString().equals("anyType{}")) {
                                            map.put(requestes_tables.get(key).get(name_num), "");
                                        } else {
                                            map.put(requestes_tables.get(key).get(name_num), Objecttable.getProperty(requestes_tables.get(key).get(name_num)).toString());
                                        }
                                    }

                                } else {
                                    map.put(requestes_tables.get(key).get(name_num), "");
                                }
                            }
//				if(Objecttable.hasProperty("Code")) {
//					map.put("Code", Objecttable.getProperty("Code").toString());
//				}else{
//					map.put("Code", "");
//				}
                            Response_arrayHashMap.get(key).add(map);
                        }
                    }



                }catch (Exception e) {
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", "Call: ", e);
            Response_arrayHashMap = new HashMap<String, ArrayList<HashMap<String, String>>>();
        }

        return Response_arrayHashMap;
    }

    public String Call_result() {
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, FunctionName);
        /**
         ParamSendName = ["x","y","z"]
         ParamSendValue = ["xvalue","yvalue","zvalue"]
         */
        for (int i = 0; i < ParamSendName.size(); i++) {
            //request.addProperty(getPI("x","xvalue"), String.class));
            request.addProperty(getPI(ParamSendName.get(i), ParamSendValue.get(i), String.class));
        }
//		request.addProperty(getPI("UserName",UserName, String.class));
        /** end send*/
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        String Result = "";
        try {
            // Invole web service
            androidHttpTransport.call(SOAP_ACTION + FunctionName, envelope);
            // Get the response

            SoapPrimitive resultsString = (SoapPrimitive) envelope.getResponse();

            Result = String.valueOf(resultsString);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", "Call: ", e);
            Result = "ERROR";
        }

        return Result;
    }

    private boolean IsImage(String request_Name) {
        if (IsImage1.equals(request_Name) || IsImage2.equals(request_Name) || IsImage3.equals(request_Name) || IsImage4.equals(request_Name)) {
            return true;
        } else {
            return false;
        }
    }


    private static PropertyInfo getPI(String name, Object value, Object Type) {
        PropertyInfo celsiusPI = new PropertyInfo();
        celsiusPI.setName(name);
        celsiusPI.setValue(value);
        celsiusPI.setType(Type);
        return celsiusPI;
    }


    private String IsImage1 = "";
    private String IsImage2 = "";
    private String IsImage3 = "";
    private String IsImage4 = "";

    public void setIsImage1(String isImage1) {
        IsImage1 = isImage1;
    }

    public void setIsImage2(String isImage2) {
        IsImage2 = isImage2;
    }

    public void setIsImage3(String isImage3) {
        IsImage3 = isImage3;
    }

    public void setIsImage4(String isImage4) {
        IsImage4 = isImage4;
    }

    public static class MarshalDouble implements Marshal {
        public Object readInstance(XmlPullParser parser, String namespace, String name,
                                   PropertyInfo expected) throws IOException, XmlPullParserException {

            return Double.parseDouble(parser.nextText());
        }


        public void register(SoapSerializationEnvelope cm) {
            cm.addMapping(cm.xsd, "double", Double.class, this);

        }


        public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
            writer.text(obj.toString());
        }
    }

    public static class MarshalShort implements Marshal {
        public Object readInstance(XmlPullParser parser, String namespace, String name,
                                   PropertyInfo expected) throws IOException, XmlPullParserException {

            return Short.parseShort(parser.nextText());
        }


        public void register(SoapSerializationEnvelope cm) {
            cm.addMapping(cm.xsd, "short", Short.class, this);

        }


        public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
            writer.text(obj.toString());
        }
    }

}
