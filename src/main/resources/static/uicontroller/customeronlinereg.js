


//Onload event
window.addEventListener('load', () => {

    //unable tooltip
    $('[data-bs-toggle="tooltip" ]').tooltip();

    refreshForm1();
    refreshForm2();


})


//Validation Of Dynamic dropdown
const dynamicElementValidator = (element, object, property) => {

    const dynamicElement = element.value;

    customer[property] = JSON.parse(dynamicElement);

    element.classList.add("is-valid");


}



//Refresh Customer Form
const refreshForm1 = () => {

    //Customer Form Record(For Data Binding)
    customer = new Object();


    customer.customer_type_id = { id: 1, type: "Individual" };

    //Cleaning attributes
    formIndividual.reset();


    //Removing Validation using a common function
    setDefault([txtCustomerName_tab1,
    txtMobileNo_tab1,
    txtEmailAddress_tab1,
    txtaddress_tab1]);

}



const refreshForm2 = () => {

    //Customer Form Record(For Data Binding)
    customer = new Object();

    // Creating a new array to pass data to the association table
    customer.customerHasItemList = new Array();

    customer.customer_type_id = { id: 1, type: "Individual" };

    formShop.reset();

    //Removing Validation using a common function
    setDefault([txtCustomerName_tab2,
        txtMobileNo_tab2,
        txtEmailAddress_tab2,
        txtaddress_tab2,
        selectVehicleRoute_tab2,
        textNote_tab2]);

    //Retriving data from the database using ajax common function defined in the commonFunctions.js
    let vehicleRoute = getServiceRequest("/vehicleroute/alldata");

    //Filling Data into the dropdown
    fillDataIntoSelect(selectVehicleRoute_tab2, "Please select Vehicle Route", vehicleRoute, "name");


    // Refreshing Inner Form and inner table
    refreshInnerFormAndTable();

}


//check errors in the form1
const checkFormError1 = () => {


    //need to check all required properties
    let errors = "";

    if (customer.name == null) {
        errors = errors + "Please Enter a valid Customer Name..!\n";

    }

    if (customer.mobileno == null) {
        errors = errors + "Please Enter a  valid Mobile Number..!\n";

    }

    if (customer.email == null) {
        errors = errors + "Please Enter a valid Email..!\n";

    }

    if (customer.address == null) {
        errors = errors + "Please Enter a valid Address..!\n";

    }
    return errors;


}

//check errors in the form1
const checkFormError2 = () => {


    //need to check all required properties
    let errors = "";

    if (customer.name == null) {
        errors = errors + "Please Enter a valid Customer Name..!\n";

    }

    if (customer.mobileno == null) {
        errors = errors + "Please Enter a  valid Mobile Number..!\n";

    }

    if (customer.email == null) {
        errors = errors + "Please Enter a valid Email..!\n";

    }

    if (customer.address == null) {
        errors = errors + "Please Enter a valid Address..!\n";

    }


    if (customer.vehicle_route_id == null) {
        errors = errors + "Please Enter a Vehicle Route...!\n";

    }

    return errors;

}




//form submit event function
const buttonCustomerSubmit1 = () => {

    console.log(customer);


    let errors = checkFormError1();
    if (errors == "") {
        let userConfirmMsg1 =
            "\n Customer Full Name :" + customer.name +
            "\n Customer Type :" + customer.customer_type_id.type +
            "\n Customer Mobile No:" + customer.mobileno +
            "\n Customer Email Address:" + customer.email +
            "\n Customer Address:" + customer.address;

        //Sweet alert function
        swal({
            title: "Are you sure to Submit Folllowing Changes..?",
            text: userConfirmMsg1,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((userResponce) => {

                if (userResponce) {
                    //call post service
                    let postResponce = getHTTPServiceRequest("/onlinecustomer/insert", "POST", customer);
                    if (postResponce == "OK") {
                        swal("Account is created Successfully ....!");

                        refreshForm1();
                        $("#customerForm").modal("hide");


                    } else {
                        swal("Failed to submit..! \n" + postResponce);

                    }

                }

            })


    } else {
        swal(errors);

    }
}

//form submit event function
const buttonCustomerSubmit2 = () => {


    let errors = checkFormError2();
    if (errors == "") {
        let userConfirmMsg2 =
            "\n Customer Full Name :" + customer.name +
            "\n Customer  Status:" + customer.customer_type_id.type +
            "\n Customer Mobile No:" + customer.mobileno +
            "\n Customer Email Address:" + customer.email +
            "\n Customer Address:" + customer.address +
            "\n Customer Route:" + customer.vehicle_route_id.name;


        //Sweet alert function
        swal({
            title: "Are you sure to Submit Folllowing Changes..?",
            text: userConfirmMsg2,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((userResponce) => {

                if (userResponce) {
                    //call post service
                    let postResponce = getHTTPServiceRequest("/onlinecustomer/insert", "POST", customer);
                    if (postResponce == "OK") {
                        swal("Account is created successfully....!");

                        refreshForm2();
                        $("#customerForm").modal("hide");

                    } else {
                        swal("Failed to submit..! \n" + postResponce);

                    }
                }

            })

    } else {
        swal(errors);
    }
}

// Refreshing Inner Form and Inner table
const refreshInnerFormAndTable  = () =>{

//     Cleaning attributes
    customerOnlineInnerForm.reset();

    //Removing Validation using a common function
    setDefault([selectItem,
        txtItemQuantity,
        selectSession]);

    customerItem = new Object();

    let item = getServiceRequest("item/alldata");

    //Filling data into dropdowns
    fillDataIntoSelect(selectItem, "Please select Item..!", item, "item_name");


    let session = getServiceRequest("/productionsession/alldata");

    //Filling data into dropdowns
    fillDataIntoSelect(selectSession, "Please select Session..!", session, "name");

    //Inner Table
    let innerColumns = [{ propertyName: getItem, dataType: "function" },
        { propertyName: "qty", dataType: "string" },
        { propertyName: getSession, dataType: "function" }];

// Calling common function to fill data into table
    fillDataIntoInnerTable(tableInnerCustomerBody, customer.customerHasItemList, innerColumns, buttonInnerCustomerRefill, buttonInnerCustomerDelete, true);


}

// Function to gt ItemName
const getItem = (dataOb) =>{

    return dataOb?.item_id?.item_name;
}


// Function to get Session
const getSession = (dataOb) =>{

    return dataOb?.production_session_id?.name ;
}


const buttonInnerCustomerRefill = () =>{

}

const buttonInnerCustomerDelete = () => {

}

const checkInnerFormError = () =>{

    let errors = "";

    if (customerItem.item_id == null) {
        errors = errors + "Please Select an Item...!\n";
    }

    if (customerItem.qty == null) {
        errors = errors + "Please Enter No Of Items...!\n";
    }

    if (customerItem.production_session_id == null) {
        errors = errors + "Please Select Session..!\n";
    }

    return errors;
}


// InnerForm Submit
const buttonInnerFormSubmit = () =>{

    console.log(customerItem);
    console.log(customer);

    //Check form error for required element
    let errors = checkInnerFormError();

    if (errors == "") {

        let userConfirmMsg3 =

            "\n Item Name :" + customerItem.item_id.item_name+
            "\n No Of Items :" +customerItem.qty+
            "\n Session :" +customerItem.production_session_id.name;

        swal({
            title: "Are you sure to add following details..?",
            text: userConfirmMsg3,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((userResponce) => {

                if (userResponce) {
                    //call post service
                    let postResponce = "OK";
                    if (postResponce == "OK") {
                        // Pushing the object of inner form "customerItem"
                        customer.customerHasItemList.push(customerItem);

                        swal("Added Successfully..!")
                        refreshInnerFormAndTable();

                    }

                }

            });

    } else {

        swal(errors);
    }
}


//Validation Of Dynamic dropdown
const dynamicElementValidator2 = (element, object, property) => {

    const dynamicElement = element.value;

    customerItem[property] = JSON.parse(dynamicElement);

    element.classList.add("is-valid");

}