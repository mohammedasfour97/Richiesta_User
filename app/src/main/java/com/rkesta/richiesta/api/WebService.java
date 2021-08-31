package com.rkesta.richiesta.api;

import java.util.ArrayList;
import java.util.HashMap;

public class WebService {

    public ArrayList<HashMap<String, String>> Callac(String Call ) {
        MasterSlayer MS = new MasterSlayer("Callac");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("Call");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(Call);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }

    public String Call_result(String SONumber) {
        MasterSlayer MS = new MasterSlayer("Call_result");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("SONumber");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(SONumber);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    /**
     * Result
     * let theID = elem.element?.text ?? ""
     *                         if(theID.isEmpty || theID.elementsEqual("0")){
     * false
     * }
     *
     *
     *
     //    http://cp.rkesta.com/prdPic/20200829213541UserProPic.jpg
     self.repare_imageURL(imageurl: "\(self.imageURL)\(((elem["ProfPic"].element?.text ?? "")?.get_after(char: "/prdPic/"))!)")
     *
     */

    //region HelpCenter
    public String ContactUS(String _Name , String _Email , String _Body ) {
        MasterSlayer MS = new MasterSlayer("ContactUS");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("_Name");
        send_params.add("_Email");
        send_params.add("_Body");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(_Name);
        send_params_value.add(_Email);
        send_params_value.add(_Body);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    //endregion

    //region MyOrder
    public ArrayList<HashMap<String, String>> SelectRK_SalesOrderByUserID(String ID) {
        MasterSlayer MS = new MasterSlayer("SelectRK_SalesOrderByUserID");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("ID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(ID);
        MS.addsendparam(send_params, send_params_value);
        /**
         *
         static var SO_Master_list : [SONumber_model] = [];

         struct SONumber_model {
         let SONumber : String
         var ShippingRate: String
         var AllTotalPrice : String
         let OrderDate : String
         let IsComplete : String
         var SO_Details_list : [MyOrders_model] = [];
         var isExpanded: Bool == !IsComplete
         }

         struct MyOrders_model {
         let ID : String
         let SONumber : String
         let StoreId : String
         let RKPrdNameAR : String
         let RKPrdNameEN : String
         let ProductPic : String
         let RK_Branch : String
         let RK_Products : String
         let ProductColor : String
         let ProductAdditionals : String
         let ProductSize : String
         let ColorUnitPrice : String
         let AdditionalUnitPrice : String
         let SizeUnitPrice : String
         let SalesUnitPrice : String
         let Qty : String
         let TotalPrice : String
         let OrderNotes : String
         let IsComplete : String
         let OrderDate : String
         let RK_User : String
         let RK_User_DetailID : String
         let Notes : String
         }

         *
         */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("SONumber");
        request_params.add("ShippingRate");
        request_params.add("AllTotalPrice");
        request_params.add("OrderDate");
        request_params.add("IsComplete");
        request_params.add("ID");
        request_params.add("SONumber");
        request_params.add("StoreId");
        request_params.add("RKPrdNameAR");
        request_params.add("RKPrdNameEN");
        request_params.add("ProductPic");
        request_params.add("RK_Branch");
        request_params.add("RK_Products");
        request_params.add("ProductColor");
        request_params.add("ProductAdditionals");
        request_params.add("ProductSize");
        request_params.add("ColorUnitPrice");
        request_params.add("AdditionalUnitPrice");
        request_params.add("SizeUnitPrice");
        request_params.add("SalesUnitPrice");
        request_params.add("Qty");
        request_params.add("TotalPrice");
        request_params.add("OrderNotes");
        request_params.add("IsComplete");
        request_params.add("OrderDate");
        request_params.add("RK_User");
        request_params.add("RK_User_DetailID");
        request_params.add("Notes");
        request_params.add("DiscountAmount");




        MS.setRequest_paramName(request_params);
        /** 3 - any image */
         MS.setIsImage1("ProductPic");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }
    //endregion

    //region login  / register
//==============login / register================
    public ArrayList<HashMap<String, String>> RK_UserLogin(String Phone , String Password ) {
        MasterSlayer MS = new MasterSlayer("RK_UserLogin");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("Phone");
        send_params.add("Password");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(Phone);
        send_params_value.add(Password);
        MS.addsendparam(send_params, send_params_value);
/**
 *
 Appshared.user_ID = elem["ID"].element?.text ?? ""
 Appshared.userAr = elem["FullNameArabic"].element?.text ?? ""
 Appshared.userEN = elem["FullNameEnglish"].element?.text ?? ""
 Appshared.userEmail = elem["Email"].element?.text ?? ""
 Appshared.userPhone =  elem["Phone"].element?.text ?? ""

 Appshared.userProPic = self.repare_imageURL(imageurl: "\(self.imageURL)\(((elem["ProfPic"].element?.text ?? "")?.get_after(char: "/prdPic/"))!)")

 Appshared.userProPicName = ((elem["ProfPic"].element?.text ?? "")?.get_after(char: "/prdPic/"))!

 Appshared.userFirstName = elem["FirstName"].element?.text ?? ""
 Appshared.userLastName = elem["LastName"].element?.text ?? ""
 Appshared.userFirstNameAR = elem["FirstNameAR"].element?.text ?? ""
 Appshared.userLastNameAR = elem["LastNameAR"].element?.text ?? ""
 Appshared.IS_Guest = false
 */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        request_params.add("FullNameArabic");
        request_params.add("FullNameEnglish");
        request_params.add("Email");
        request_params.add("Phone");
        request_params.add("ProfPic");
        request_params.add("FirstName");
        request_params.add("LastName");
        request_params.add("FirstNameAR");
        request_params.add("LastNameAR");

        MS.setRequest_paramName(request_params);
        /** 3 - any image */
         MS.setIsImage1("ProfPic");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }

//	==============REgiste / Edit Profile================
    public String UploadFile(String fle , String FileName ) {
        MasterSlayer MS = new MasterSlayer("UploadFile");

        MS.URL = "http://cvr.rkesta.info/Common.asmx";

        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("fle");
        send_params.add("FileName");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(fle);
        send_params_value.add(FileName);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    public String VerifyPhoneNumber(String _phone) {
        /**
         *
         //        <VerifyPhoneNumberResult>Y</VerifyPhoneNumberResult>
         //        <VerifyPhoneNumberResult>N</VerifyPhoneNumberResult>
         * */
        MasterSlayer MS = new MasterSlayer("VerifyPhoneNumber");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("_phone");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(_phone);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    //endregion


    //region Cart

    public ArrayList<HashMap<String, String>> SelectRK_Branch_DetailsByBranchID(String ID) {
        MasterSlayer MS = new MasterSlayer("SelectRK_Branch_DetailsByBranchID");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("ID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(ID);
        MS.addsendparam(send_params, send_params_value);
/**
 *
 * RK_BranchDetailID = elem["ID"].element?.text ?? ""
 *
 * */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call();

    }

    public String sprk_CalcDeliveryAmount(String RK_BranchID , String  RK_UserDetailID ) {
        MasterSlayer MS = new MasterSlayer("sprk_CalcDeliveryAmount");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("RK_BranchID");
        send_params.add("RK_UserDetailID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(RK_BranchID);
        send_params_value.add(RK_UserDetailID);
        MS.addsendparam(send_params, send_params_value);
/**
 *
 * DeliveryAmount = elem.element?.text ?? "0.0"
 *
 * */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }
    public String SendConfirmationEmail(String OrderNumber , String OrderDate, String CustomerDisplayName , String  CustomerEmail ,
                                        String CustomerPhone , String OrderTotal  ) {
        MasterSlayer MS = new MasterSlayer("SendConfirmationEmail");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("OrderNumber");
        send_params.add("OrderDate");
        send_params.add("CustomerDisplayName");
        send_params.add("CustomerEmail");
        send_params.add("CustomerPhone");
        send_params.add("OrderTotal");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(OrderNumber);
        send_params_value.add(OrderDate);
        send_params_value.add(CustomerDisplayName);
        send_params_value.add(CustomerEmail);
        send_params_value.add(CustomerPhone);
        send_params_value.add(OrderTotal);
        MS.addsendparam(send_params, send_params_value);
        /**
         * Result
         *  GET_Response.SONumber =  elem.element?.text ?? ""
         */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();
    }

    public String GenSONumber(String _productid , String _storeid ) {
        MasterSlayer MS = new MasterSlayer("GenSONumber");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("_productid");
        send_params.add("_storeid");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(_productid);
        send_params_value.add(_storeid);
        MS.addsendparam(send_params, send_params_value);
        /**
         * Result
         *  GET_Response.SONumber =  elem.element?.text ?? ""
         */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    public ArrayList<HashMap<String, String>> InsertRK_SalesOrder(String SONumber , String StoreId , String  RK_Branch , String  RK_Products  ,
                                                                  String RK_ProductColor , String RK_ProductAdditional , String RK_ProductSize ,
                                                                  String ColorUnitPrice , String  AdditionalUnitPrice , String SizeUnitPrice  ,
                                                                  String SalesUnitPrice , String Qty , String OrderNote , String RK_User ,
                                                                  String RK_User_DetailID , String Notes , String  Createdby ,
                                                                  String ShippingRate) {
        MasterSlayer MS = new MasterSlayer("InsertRK_SalesOrder");

        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("SONumber");
        send_params.add("StoreId");
        send_params.add("RK_Branch");
        send_params.add("RK_Products");
        send_params.add("RK_ProductColor");
        send_params.add("RK_ProductAdditional");
        send_params.add("RK_ProductSize");
        send_params.add("ColorUnitPrice");
        send_params.add("AdditionalUnitPrice");
        send_params.add("SizeUnitPrice");
        send_params.add("SalesUnitPrice");
        send_params.add("Qty");
        send_params.add("OrderNote");
        send_params.add("RK_User");
        send_params.add("RK_User_DetailID");
        send_params.add("Notes");
        send_params.add("Createdby");
        send_params.add("ShippingRate");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(SONumber);
        send_params_value.add(StoreId);
        send_params_value.add(RK_Branch);
        send_params_value.add(RK_Products);
        send_params_value.add(RK_ProductColor);
        send_params_value.add(RK_ProductAdditional);
        send_params_value.add(RK_ProductSize);
        send_params_value.add(ColorUnitPrice);
        send_params_value.add(AdditionalUnitPrice);
        send_params_value.add(SizeUnitPrice);
        send_params_value.add(SalesUnitPrice);
        send_params_value.add(Qty);
        send_params_value.add(OrderNote);
        send_params_value.add(RK_User);
        send_params_value.add(RK_User_DetailID);
        send_params_value.add(Notes);
        send_params_value.add(Createdby);
        send_params_value.add(ShippingRate);
        MS.addsendparam(send_params, send_params_value);

//        Result
//        let theID = elem.element?.text ?? ""
//        if(theID.isEmpty || theID.elementsEqual("0")){
//            false
//        }

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call();

    }


    //endregion


    //region myaddress

    /**
     *  get user details
     */
    public ArrayList<HashMap<String, String>> SelectRK_User_DetailsByUserID(String ID ) {
        MasterSlayer MS = new MasterSlayer("SelectRK_User_DetailsByUserID");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("ID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(ID);
        MS.addsendparam(send_params, send_params_value);
/**
 *
 struct MyAddress_model {

 let ID : String
 let RKUserId : String
 let RKAddress : String
 let RKBldngNum : String
 let RKFloorNum : String
 let RKAptNum : String
 let RKCity : String
 let RKCountry : String
 let Createdby : String
 let Notes : String
 }

 GET_Response.ID_Address.append(Int(elem["ID"].element?.text ?? "0")!)
 GET_Response.Address.append(elem["RKAddress"].element?.text ?? "")
 */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        request_params.add("RKUserId");
        request_params.add("RKAddress");
        request_params.add("RKBldngNum");
        request_params.add("RKFloorNum");
        request_params.add("RKAptNum");
        request_params.add("RKCity");
        request_params.add("RKCountry");
        request_params.add("Longitude");
        request_params.add("Latitude");
        request_params.add("Createdby");
        request_params.add("Notes");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }

    /**
     *  modify
     */
    //================myAddress / EditProfile==============
    public String ModifyRK_User(String ID , String FirstName , String LastName , String FirstNameAR , String LastNameAR , String  PassPhrase ,
                                String Email , String Phone , String CellPhone , String CellPhone2 , String ProfPic , boolean isActive,
                                boolean  isBanned , String Createdby,String Notes ) {
        MasterSlayer MS = new MasterSlayer("ModifyRK_User");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("ID");
        send_params.add("FirstName");
        send_params.add("LastName");
        send_params.add("FirstNameAR");
        send_params.add("LastNameAR");
        send_params.add("PassPhrase");
        send_params.add("Email");
        send_params.add("Phone");
        send_params.add("CellPhone");
        send_params.add("CellPhone2");
        send_params.add("ProfPic");
        send_params.add("isActive");
        send_params.add("isBanned");
        send_params.add("Createdby");
        send_params.add("Notes");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(ID);
        send_params_value.add(FirstName);
        send_params_value.add(LastName);
        send_params_value.add(FirstNameAR);
        send_params_value.add(LastNameAR);
        send_params_value.add(PassPhrase);
        send_params_value.add(Email);
        send_params_value.add(Phone);
        send_params_value.add(CellPhone);
        send_params_value.add(CellPhone2);
        send_params_value.add("../../prdPic/" + ProfPic);
        send_params_value.add(isActive+"");
        send_params_value.add(isBanned+"");
        send_params_value.add(Createdby);
        send_params_value.add(Notes);
        MS.addsendparam(send_params, send_params_value);
/**
 *

 if(theID.isEmpty || theID.elementsEqual("0")){
 GET_Response.IS_done_Insert_RKUser_request = false
 }else{//1 Done
 GET_Response.IS_done_Insert_RKUser_request = true


 self.InsertRK_User_Details(RKUserId: theID, RKAddress: "", RKBldngNum: "", RKFloorNum: "", RKAptNum: "", RKCity: Appshared.ID_City, RKCountry: Appshared.ID_Country, longitude: "0.0", latitude: "0.0", Createdby: "IOS_RegisterClass_into_funcInsertRK_User", Notes: "")
 }

 */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call_result();
    }
    public String ModifyRK_User_Details(String ID , String RKUserId , String RKAddress , String  RKBldngNum , String  RKFloorNum
            , String RKAptNum , String  RKCity , String RKCountry , String longitude, String latitude , String Createdby , String Notes ) {

        MasterSlayer MS = new MasterSlayer("ModifyRK_User_Details");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("ID");
        send_params.add("RKUserId");
        send_params.add("RKAddress");
        send_params.add("RKBldngNum");
        send_params.add("RKFloorNum");
        send_params.add("RKAptNum");
        send_params.add("RKCity");
        send_params.add("RKCountry");
        send_params.add("longitude");
        send_params.add("latitude");
        send_params.add("Createdby");
        send_params.add("Notes");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(ID);
        send_params_value.add(RKUserId);
        send_params_value.add(RKAddress);
        send_params_value.add(RKBldngNum);
        send_params_value.add(RKFloorNum);
        send_params_value.add(RKAptNum);
        send_params_value.add(RKCity);
        send_params_value.add(RKCountry);
        send_params_value.add(longitude);
        send_params_value.add(latitude);
        send_params_value.add(Createdby);
        send_params_value.add(Notes);
        MS.addsendparam(send_params, send_params_value);
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        MS.setRequest_paramName(request_params);
        /** 3 - any image */

        return MS.Call_result();
    }

    /**
     * Insert
     */

    //==============register / cv_myaddress================
    public ArrayList<HashMap<String, String>> InsertRK_User(String FirstName , String LastName , String FirstNameAR , String LastNameAR ,
                                                            String  PassPhrase , String Email , String Phone , String CellPhone ,
                                                            String CellPhone2 , String ProfPic , boolean isActive, boolean  isBanned ,
                                                            String Createdby,String Notes ) {
        MasterSlayer MS = new MasterSlayer("InsertRK_User");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("FirstName");
        send_params.add("LastName");
        send_params.add("FirstNameAR");
        send_params.add("LastNameAR");
        send_params.add("PassPhrase");
        send_params.add("Email");
        send_params.add("Phone");
        send_params.add("CellPhone");
        send_params.add("CellPhone2");
        send_params.add("ProfPic");
        send_params.add("isActive");
        send_params.add("isBanned");
        send_params.add("Createdby");
        send_params.add("Notes");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(FirstName);
        send_params_value.add(LastName);
        send_params_value.add(FirstNameAR);
        send_params_value.add(LastNameAR);
        send_params_value.add(PassPhrase);
        send_params_value.add(Email);
        send_params_value.add(Phone);
        send_params_value.add(CellPhone);
        send_params_value.add(CellPhone2);
        send_params_value.add(ProfPic);
        send_params_value.add(isActive+"");
        send_params_value.add(isBanned+"");
        send_params_value.add(Createdby);
        send_params_value.add(Notes);
        MS.addsendparam(send_params, send_params_value);
/**
 *

 if(theID.isEmpty || theID.elementsEqual("0")){
 GET_Response.IS_done_Insert_RKUser_request = false
 }else{//1 Done
 GET_Response.IS_done_Insert_RKUser_request = true


 self.InsertRK_User_Details(RKUserId: theID, RKAddress: "", RKBldngNum: "", RKFloorNum: "", RKAptNum: "", RKCity: Appshared.ID_City, RKCountry: Appshared.ID_Country, longitude: "0.0", latitude: "0.0", Createdby: "IOS_RegisterClass_into_funcInsertRK_User", Notes: "")
 }

 */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }

    public ArrayList<HashMap<String, String>> InsertRK_User_Details(String RKUserId , String RKAddress , String  RKBldngNum , String  RKFloorNum
                                                                    , String RKAptNum , String  RKCity , String RKCountry , String longitude, String latitude , String Createdby , String Notes ) {

        MasterSlayer MS = new MasterSlayer("InsertRK_User_Details");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("RKUserId");
        send_params.add("RKAddress");
        send_params.add("RKBldngNum");
        send_params.add("RKFloorNum");
        send_params.add("RKAptNum");
        send_params.add("RKCity");
        send_params.add("RKCountry");
        send_params.add("longitude");
        send_params.add("latitude");
        send_params.add("Createdby");
        send_params.add("Notes");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(RKUserId);
        send_params_value.add(RKAddress);
        send_params_value.add(RKBldngNum);
        send_params_value.add(RKFloorNum);
        send_params_value.add(RKAptNum);
        send_params_value.add(RKCity);
        send_params_value.add(RKCountry);
        send_params_value.add(longitude);
        send_params_value.add(latitude);
        send_params_value.add(Createdby);
        send_params_value.add(Notes);
        MS.addsendparam(send_params, send_params_value);
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */

        // xml["soap:Envelope"]["soap:Body"]["\(soapAction)Response"]["\(soapAction)Result"]["diffgr:diffgram"]["DocumentElement"]["sprk_\(soapAction)"].all {
        //                        //print(elem.description)
        //
        //                        let theID = elem["ID"].element?.text ?? ""
        //                        print(theID)
        //                        if(theID.isEmpty || theID.elementsEqual("0")){
        //                            GET_Response.IS_done_Insert_MyAddress_request = false
        //                        }else{//1 Done
        //                            GET_Response.IS_done_Insert_MyAddress_request = true
        //                        }

        return MS.Call();
    }

    /**
     * delete
     */
    public String DeleteRK_User_Details(String ID) {
        MasterSlayer MS = new MasterSlayer("DeleteRK_User_Details");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("ID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(ID);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");


//

        return MS.Call_result();
    }

    //endregion


    //region popup
// ============== popupPass ================
    public String UptPass(String RKUserId , String OldPW , String NewPW) {
        MasterSlayer MS = new MasterSlayer("UptPass");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("RKUserId");
        send_params.add("OldPW");
        send_params.add("NewPW");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(RKUserId);
        send_params_value.add(OldPW);
        send_params_value.add(NewPW);
        MS.addsendparam(send_params, send_params_value);

        /**
         *
         let theID = elem.element?.text ?? ""
         if(theID.isEmpty || theID.elementsEqual("0")){
         false
         }
         */

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }
    public String UptEmail(String RKUserId , String PW , String NewEmail) {
        MasterSlayer MS = new MasterSlayer("UptPass");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("RKUserId");
        send_params.add("PW");
        send_params.add("NewEmail");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(RKUserId);
        send_params_value.add(PW);
        send_params_value.add(NewEmail);
        MS.addsendparam(send_params, send_params_value);

        /**
         *
         let theID = elem.element?.text ?? ""
         if(theID.isEmpty || theID.elementsEqual("0")){
         false
         }
         */

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
//        request_params.add("id");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call_result();

    }

    //endregion

//splashscreen / login / view controller / cv_changeArea
    public ArrayList<HashMap<String, String>> Top6Stores
    (String CityID , String CountryID , String lat, String _long) {
//["NewDataSet"]["sprk_apr20Top6ProdsPerStoreWithLocation"]
//["NewDataSet"]["sprk_SelectRK_Tag"]
        MasterSlayer MS = new MasterSlayer("Top6Stores");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("CityID");
        send_params.add("CountryID");
        send_params.add("Lat");
        send_params.add("Long");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(CityID);
        send_params_value.add(CountryID);
        send_params_value.add(lat);
        send_params_value.add(_long);
        MS.addsendparam(send_params, send_params_value);

        ArrayList<String> request_params = new ArrayList<String>();

        request_params.add("ProductCode");
        request_params.add("NameArabic");
        request_params.add("NameEnglish");
        request_params.add("DescriptionArabic");
        request_params.add("DescriptionEnglish");
        request_params.add("BranchCode");
        request_params.add("BranchNameAr");
        request_params.add("BranchNameEN");
        request_params.add("StoreID");
        request_params.add("StoreNameAr");
        request_params.add("StoreNameEN");
        request_params.add("RKStoreCategoryID");
        request_params.add("StoreCategoryNameAr");
        request_params.add("StoreCategoryNameEn");
        request_params.add("StoreCategoryPic");
        request_params.add("ProductNumber");
        request_params.add("ProductUPC");
        request_params.add("SalesUnitPrice");
        request_params.add("IsAvailable");
        request_params.add("ProductCategory");
        request_params.add("CategoryNameArabic");
        request_params.add("CategoryNameEnglish");
        request_params.add("Notes");
        request_params.add("StoreCategoryPic");
        request_params.add("ProductImage");
        request_params.add("ProductImage");
        request_params.add("StoreLogo");

        MS.setRequest_paramName(request_params);

        MS.setIsImage1("StoreCategoryPic");
        MS.setIsImage2("ProductImage");
        MS.setIsImage3("ProductImage");
        MS.setIsImage4("StoreLogo");

        return MS.Call();

    }

    //region slidercell (select) / shop (refresh)

    public ArrayList<HashMap<String, String>> Selectsprk_vw_MayProductDetailsByStoreId(String _StoreId) {

        MasterSlayer MS = new MasterSlayer("Selectsprk_vw_MayProductDetailsByStoreId");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("_StoreId");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(_StoreId);
        MS.addsendparam(send_params, send_params_value);
        /**
         *

         need to check it

         BranchLongitude = 0.0
         BranchLatitude = 0.0
         BranchDescription = ""
         BranchAddress = ""
         BranchPhone = ""
         BranchCell = ""

         product_ID
         nameEN
         nameAR
         DescEn
         DescAr
         price
         imageurl
         UnitType
         UnitTypeAr
         category_ID
         branch_ID

//         ProdDetID
//         ProdDetStoreId
//         ProdDetBranchId
//         BranchLongitude
//         BranchLatitude
//         BranchDescription
//         BranchAddress
//         BranchPhone
//         BranchCell
//         ProdDetProdID
//         ProdNameEN
//         ProdNameAR
//         ProductDescAr
//         ProductDescEn
//         ProductPic
//         ProdDetCategoryId
//         ProdDetDetId
//         ProdDetHexCode
//         ProdDetNameEN
//         ProdDetNameAR
//         ProdDetUnitType
//         ProductSalesUnitPrice
//         ProductDetUnitPrice
//         MeasurmentUnit
//         MeasurmentUnitAr
//         ProdDetType

         ProdDetID
         ProdDetStoreId
         ProdDetBranchId
         ProdDetProdID
         ProdNameEN
         ProdNameAR
         ProductDescAr
         ProductDescEn
         ProductPic
         ProdDetCategoryId
         ProdDetDetId
         ProdDetHexCode
         ProdDetNameEN
         ProdDetNameAR
         ProdDetUnitType
         ProductSalesUnitPrice
         ProductDetUnitPrice
         MeasurmentUnit
         MeasurmentUnitAr
         ProdDetType

         <ProdDetID>111</ProdDetID>
         <ProdDetStoreId>10</ProdDetStoreId>
         <ProdDetProdID>1230</ProdDetProdID><
         ProdNameEN>Double Whopper Meal</ProdNameEN>
         <ProdNameAR>دبل وبر ميل</ProdNameAR>
         <ProductPic>~/prdPic//9ea2ed_d161176839e847d9a1e4365ef8005fb3.png</ProductPic>
         <ProdDetCategoryId>10</ProdDetCategoryId>
         <ProdDetDetId>10</ProdDetDetId>
         <ProdDetHexCode></ProdDetHexCode>
         <ProdDetNameEN>test</ProdDetNameEN>
         <ProdDetNameAR>test</ProdDetNameAR>
         <ProdDetUnitType>test</ProdDetUnitType>
         <ProductSalesUnitPrice>97.35</ProductSalesUnitPrice>
         <ProductDetUnitPrice>0</ProductDetUnitPrice>
         <ProdDetType>ProdSize</ProdDetType>
         </sprk_vw_MayProductDetailsByStoreId>


         static var productBySID_list: [product_model] = [];

         struct product_model {
         let product_ID: String
         let nameEN: String
         let nameAR: String
         let DescEn: String
         let DescAr: String
         let price: String
         let imageurl: String
         let UnitType: String
         let UnitTypeAr: String
         let category_ID: String
         let branch_ID: String
         }

         static var ExpandableCSA_list : [ExpandableCSA] =
         [
         ExpandableCSA(name:"Color",CSA_list: [],isExpanded: true, selectednumb: -1),

         ExpandableCSA(name:"Size",CSA_list: [],isExpanded: true, selectednumb: -1),

         ExpandableCSA(name:"additional",CSA_list: [],isExpanded: true, selectednumb: -1)
         ];

         //if((elem["ProdDetType"].element?.text ?? "").elementsEqual("ProdColor") && !((elem["ProdDetID"].element?.text ?? "").elementsEqual(""))){//0

         //if((elem["ProdDetType"].element?.text ?? "").elementsEqual("ProdSize") && !((elem["ProdDetID"].element?.text ?? "").elementsEqual(""))){//1

         //if((elem["ProdDetType"].element?.text ?? "").elementsEqual("ProdAdditional") && !((elem["ProdDetID"].element?.text ?? "").elementsEqual(""))){//2


         struct ExpandableCSA {
         let name: String
         var CSA_list: [CSA]
         var isExpanded: Bool
         var selectednumb: Int
         }


         struct CSA {
         let ProductID: String
         let ID: String
         let HexCode: String
         let NameEN: String
         let NameAR: String
         let Price: String
         }
         */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ProdDetID");
        request_params.add("ProdDetStoreId");
        request_params.add("ProdDetBranchId");
        request_params.add("BranchLongitude");
        request_params.add("BranchLatitude");
        request_params.add("BranchDescription");
        request_params.add("BranchAddress");
        request_params.add("BranchPhone");
        request_params.add("BranchCell");
        request_params.add("ProdDetProdID");
        request_params.add("ProdNameEN");
        request_params.add("ProdNameAR");
        request_params.add("ProductDescAr");
        request_params.add("ProductDescEn");
        request_params.add("ProductPic");
        request_params.add("ProdDetCategoryId");
        request_params.add("ProdDetDetId");
        request_params.add("ProdDetHexCode");
        request_params.add("ProdDetNameEN");
        request_params.add("ProdDetNameAR");
        request_params.add("ProdDetUnitType");
        request_params.add("ProductSalesUnitPrice");
        request_params.add("ProductDetUnitPrice");
        request_params.add("MeasurmentUnit");
        request_params.add("MeasurmentUnitAr");
        request_params.add("ProdDetType");

        MS.setRequest_paramName(request_params);
        /** 3 - any image */
         MS.setIsImage1("ProductPic");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }


    public ArrayList<HashMap<String, String>> Selectsprk_SelectRK_CategoriesByStoreId(String _StoreId) {
        MasterSlayer MS = new MasterSlayer("Selectsprk_SelectRK_CategoriesByStoreId");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("_StoreId");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(_StoreId);
        MS.addsendparam(send_params, send_params_value);
        /**
         *
         struct StoreCategories {
         let ID: String
         let NameEN: String
         let NameAr: String
         let Notes: String
         }

         ID : elem["ID"].element?.text ?? ""
         ,NameEN:  elem["CategoryNameEN"].element?.text ?? ""
         ,NameAr:  elem["CategoryNameAr"].element?.text ?? ""
         ,Notes:  elem["CategoryNotes"].element?.text ?? ""
         */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        request_params.add("CategoryNameEN");
        request_params.add("CategoryNameAr");
        request_params.add("CategoryNotes");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }
    //endregion




    //region plashscreen
    public ArrayList<HashMap<String, String>> SelectRK_StoreCategories() {
        MasterSlayer MS = new MasterSlayer("SelectRK_StoreCategories");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        ArrayList<String> send_params_value = new ArrayList<String>();
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        request_params.add("CategoryNameEN");
        request_params.add("CategoryNameAr");
        request_params.add("RK_StoreCategories");
        request_params.add("CategoryNotes");
        request_params.add("CreatedBy");
        request_params.add("CreatedDate");
        request_params.add("ModifiedBy");
        request_params.add("ModifiedDate");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }

    public ArrayList<HashMap<String, String>> SelectCityV2() {

        MasterSlayer MS = new MasterSlayer("SelectCityV2");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        ArrayList<String> send_params_value = new ArrayList<String>();
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        request_params.add("CountryId");
        request_params.add("Code");
        request_params.add("CityAR");
        request_params.add("CityEN");
        request_params.add("Longitude");
        request_params.add("Latitude");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }
    public ArrayList<HashMap<String, String>> SelectCountryV2() {
        MasterSlayer MS = new MasterSlayer("SelectCountryV2");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        ArrayList<String> send_params_value = new ArrayList<String>();
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("ID");
        request_params.add("Code");
        request_params.add("CountryCodeAbbr3");
        request_params.add("ARCountry");
        request_params.add("ENCountry");
        request_params.add("CurrencyCode");
        request_params.add("CurrencySymbol");
        request_params.add("DialCode");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
        // MS.setIsImage1("ProductPicture");
        // MS.setIsImage2("Voice");

        return MS.Call();
    }
    //endregion


    public ArrayList<HashMap<String, String>> getPendingOrders(String user_id) {
        MasterSlayer MS = new MasterSlayer("SelectPendingOrderGetDelivID");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("userid");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(user_id);
        MS.addsendparam(send_params, send_params_value);
/**
 *
 Appshared.user_ID = elem["ID"].element?.text ?? ""
 Appshared.userAr = elem["FullNameArabic"].element?.text ?? ""
 Appshared.userEN = elem["FullNameEnglish"].element?.text ?? ""
 Appshared.userEmail = elem["Email"].element?.text ?? ""
 Appshared.userPhone =  elem["Phone"].element?.text ?? ""

 Appshared.userProPic = self.repare_imageURL(imageurl: "\(self.imageURL)\(((elem["ProfPic"].element?.text ?? "")?.get_after(char: "/prdPic/"))!)")

 Appshared.userProPicName = ((elem["ProfPic"].element?.text ?? "")?.get_after(char: "/prdPic/"))!

 Appshared.userFirstName = elem["FirstName"].element?.text ?? ""
 Appshared.userLastName = elem["LastName"].element?.text ?? ""
 Appshared.userFirstNameAR = elem["FirstNameAR"].element?.text ?? ""
 Appshared.userLastNameAR = elem["LastNameAR"].element?.text ?? ""
 Appshared.IS_Guest = false
 */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("SONumber");
        request_params.add("StoreId");
        request_params.add("DeliveryProgess_DeliveryContact");
        request_params.add("DeliveryContactNameAr");
        request_params.add("DeliveryContactNameEn");
        request_params.add("DeliveryContactPhone");
        request_params.add("DeliveryContactCell");
        request_params.add("DeliveryContactCell2");
        request_params.add("RK_Branch");
        request_params.add("RKBranch_NameAR");
        request_params.add("RKBranch_NameEN");
        request_params.add("RKBranchDetails_Longitude");
        request_params.add("RKBranchDetails_Latitude");
        request_params.add("RK_Stores_StoreNameArabic");
        request_params.add("RK_Stores_StoreNameEnglish");
        request_params.add("RK_Products");
        request_params.add("ProfPic");
        request_params.add("ColorUnitPrice");
        request_params.add("RKPrdNameEN");
        request_params.add("AdditionalUnitPrice");
        request_params.add("SizeUnitPrice");
        request_params.add("SalesUnitPrice");
        request_params.add("Qty");
        request_params.add("TotalPrice");
        request_params.add("OrderNotes");
        request_params.add("IsComplete");
        request_params.add("OrderDate");
        request_params.add("RK_User");
        request_params.add("RK_User_DetailID");
        request_params.add("UserLong");
        request_params.add("UserLat");
        request_params.add("Notes");
        request_params.add("CreatedBy");
        request_params.add("CreatedDate");
        request_params.add("ModifiedBy");
        request_params.add("ModifiedDate");
        request_params.add("ShippingRate");
        request_params.add("ProductPic");


        MS.setIsImage1("ProductPic");

        MS.setRequest_paramName(request_params);

        return MS.Call();
    }

    public ArrayList<HashMap<String, String>> getDeliveryLocation(String delivery_id) {
        MasterSlayer MS = new MasterSlayer("GetCurrentDelivLocation");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("DeliveryId");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(delivery_id);
        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("RK_DeliveryContact");
        request_params.add("Longitude");
        request_params.add("Latitude");
        request_params.add("isDeleted");
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");

        return MS.Call();

    }

    public ArrayList<HashMap<String, String>> getCheckValidateAddress(String Longitude,String Latitude,String BranchID) {
        MasterSlayer MS = new MasterSlayer("CheckAllowedDeliv");
        /**1 - any parameter send */
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("Longitude");
        send_params.add("Latitude");
        send_params.add("BranchID");
        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(Longitude);
        send_params_value.add(Latitude);
        send_params_value.add(BranchID);
        MS.addsendparam(send_params, send_params_value);
/**
 *
 Appshared.user_ID = elem["ID"].element?.text ?? ""
 Appshared.userAr = elem["FullNameArabic"].element?.text ?? ""
 Appshared.userEN = elem["FullNameEnglish"].element?.text ?? ""
 Appshared.userEmail = elem["Email"].element?.text ?? ""
 Appshared.userPhone =  elem["Phone"].element?.text ?? ""

 Appshared.userProPic = self.repare_imageURL(imageurl: "\(self.imageURL)\(((elem["ProfPic"].element?.text ?? "")?.get_after(char: "/prdPic/"))!)")

 Appshared.userProPicName = ((elem["ProfPic"].element?.text ?? "")?.get_after(char: "/prdPic/"))!

 Appshared.userFirstName = elem["FirstName"].element?.text ?? ""
 Appshared.userLastName = elem["LastName"].element?.text ?? ""
 Appshared.userFirstNameAR = elem["FirstNameAR"].element?.text ?? ""
 Appshared.userLastNameAR = elem["LastNameAR"].element?.text ?? ""
 Appshared.IS_Guest = false
 */
        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();
        request_params.add("id");

        MS.setRequest_paramName(request_params);

        return MS.Call();
    }

    public ArrayList<HashMap<String, String>> getCheckPromoCode(String PromoCode,String userid) {
        MasterSlayer MS = new MasterSlayer("CheckPromoCode");
        /**1 - any parameter send */
        
        ArrayList<String> send_params = new ArrayList<String>();
        send_params.add("PromoCode");
        send_params.add("userid");

        ArrayList<String> send_params_value = new ArrayList<String>();
        send_params_value.add(PromoCode);
        send_params_value.add(userid);

        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();

        request_params.add("id");
        request_params.add("PromoCode");
        request_params.add("AppliesTo");
        request_params.add("ValidFrom");
        request_params.add("ValidTo");
        request_params.add("ProvidedBy");
        request_params.add("Amount");
        request_params.add("AmountType");
        request_params.add("QtyLimit");
        request_params.add("userid");
        request_params.add("FreeOrder");
        request_params.add("MaxPercentAmountLimit");
        request_params.add("Prize");
        request_params.add("PrizeType");
        request_params.add("SpecificUser");
        request_params.add("userid");
        request_params.add("IsGift");
        request_params.add("RK_Gifts");
        request_params.add("PromoDesc");
        request_params.add("Notes");
        request_params.add("IsActive");
        request_params.add("IsDeleted");
        request_params.add("CreatedBy");
        request_params.add("CreatedDate");
        request_params.add("ModifiedBy");
        request_params.add("ModifiedDate");
        request_params.add("MaxUsage");
        request_params.add("UserUsageLimit");
        request_params.add("GiftCode");
        request_params.add("GiftDesc");
        request_params.add("UserPromoID");
        request_params.add("RK_User");
        request_params.add("id");
        request_params.add("UserPromoUsageCount");
        request_params.add("Column1");
        
        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");


//

        return MS.Call();
    }

    public ArrayList<HashMap<String, String>> InsertOrderSummary(String soNumber, String shippingRate,String branchId,String promoId,
                                                                 String discountAmount,String notes) {
        MasterSlayer MS = new MasterSlayer("InsertOrderSummary");
        /**1 - any parameter send */

        ArrayList<String> send_params = new ArrayList<String>();

        send_params.add("SONumber");
        send_params.add("Shippingrate");
        send_params.add("branchid");
        send_params.add("promoid");
        send_params.add("DiscountAmount");
        send_params.add("notes");

        ArrayList<String> send_params_value = new ArrayList<String>();

        send_params_value.add(soNumber);
        send_params_value.add(shippingRate);
        send_params_value.add(branchId);
        send_params_value.add(promoId);
        send_params_value.add(discountAmount);
        send_params_value.add(notes);

        MS.addsendparam(send_params, send_params_value);

        /**2 - request */
        ArrayList<String> request_params = new ArrayList<String>();

        //request_params.add("id");

        MS.setRequest_paramName(request_params);
        /** 3 - any image */
//        MS.setIsImage1("ProductPicture");
//        MS.setIsImage2("Voice");


//

        return MS.Call();
    }
}