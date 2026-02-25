
//Window onload function
window.addEventListener('load', () => {
    refreshForm1();
    refreshForm2();

})


//JavaScript to toggle collapse  ---> Delivery Method collapse

const deliveryRadio = document.getElementById('deliveryRadio');
const pickupRadio = document.getElementById('pickupRadio');
const collapseTarget = document.getElementById('deliveryDetails');
const bsCollapse = new bootstrap.Collapse(collapseTarget, { toggle: false });

deliveryRadio.addEventListener('change', () => {
    if (deliveryRadio.checked) {
        bsCollapse.show();
    }
});

pickupRadio.addEventListener('change', () => {
    if (pickupRadio.checked) {
        bsCollapse.hide();
    }
});




//JavaScript to toggle collapse  ---> Order nature(Shop Customer)

const recurentRadio = document.getElementById('recurrentRadio');
const justOnceRadio = document.getElementById('justOnceRadio');
const recurentDetails = document.getElementById('recurentDetails');
const bsCollapse2 = new bootstrap.Collapse(recurentDetails, { toggle: false });

recurentRadio.addEventListener('change', () => {
    if (recurentRadio.checked) {
        bsCollapse2.show();
    }
});

justOnceRadio.addEventListener('change', () => {
    if (justOnceRadio.checked) {
        bsCollapse2.hide();
    }
});





//Validation Of Dynamic dropdown
const dynamicElementValidator = (element, object, property) => {

    const dynamicElement = element.value;

    customerorder[property] = JSON.parse(dynamicElement);

    element.classList.add("is-valid");


}

const radioElementValidator = () => {

    if (pickupRadio.checked) {
        customerorder.collection_method_id = { id: 1, name: "To be Collected" };

    }

    if (deliveryRadio.checked) {
        customerorder.collection_method_id = { id: 2, name: "To be Delivered" };

    }

}


const orderNatureRadioValidator = () => {

    if (justOnceRadio.checked) {
        customerorder.customer_order_nature_id = { id: 1, nature: "just once" };

    }
    if (recurrentRadio.checked) {
        customerorder.customer_order_nature_id = { id: 2, nature: "recurrent" };

    }
}
//............................................................
const discountPriceGenerator = (dataOb) => {
    const status = dataOb?.customer_id?.customer_status_id?.status;

    if (status == null) {
        console.log("status is not present");

    }

    const totalPrice = parseFloat(textTotalPrice1.value);
    let discountRatio = 0;

    switch (status) {
        case "Friend":
            discountRatio = 3;
            break;
        case "Family/Relation":
            discountRatio = 5;
            break;
        case "Charity":
            discountRatio = 4;
            break;
        case "Social Service":
            discountRatio = 2;
            break;
        case "Normal":
        default:
            discountRatio = 0;
    }

    const discountedPrice = totalPrice - (totalPrice * discountRatio / 100);
    textDiscountedPrice.value = discountedPrice.toFixed(2);
    textDiscountedPrice.classList.add("is-valid");
    customerorder.discounted_price = textDiscountedPrice.value;
};

const generateDuePayment = () => {

    let discountedPrice = textDiscountedPrice.value;
    let advancedPayment = textAdvancePayment1.value;

    let duePayment = parseFloat(discountedPrice) - parseFloat(advancedPayment);

    textDuePayment1.value = parseFloat(duePayment).toFixed(2);
    customerorder.due_payment = textDuePayment1.value;
    textDuePayment1.classList.add("is-valid");


}



//refresh order form --> Individual Customer
const refreshForm1 = () => {

    //create a new object for databinding at frontend
    customerorder = new Object();

    //When Individual tab get selected, the value of customer order type of customerorder object is binded
    customerorder.customer_order_type_id = { id: 1, type: "Individual" }

    //Cleaning attributes
    formIndividual.reset();


    //Cleaning Radio buttons (Delivery Method)
    pickupRadio.checked = false;
    deliveryRadio.checked = false;


    //Removing Validation using a common function
    setDefault([selectCustomerName1,
        textTotalPrice1,
        dateRequiredDate1,
        textRequiredTime1,
        textDeliveryAddress1,
        selectDeliveryRoute1,
        textAdvancePayment1,
        textDuePayment1]);

    //Retriving customername from logged customer
    let customername = getServiceRequest("/customerlogin/customernamebyusername")
    console.log(customername);
    //filling data into dropdown
    fillDataIntoSelect(selectCustomerName1, "Please select Customer Name", customername, "name");


    //Retriving data from the data base using ajax common function defined in the coomonFunctions.js
    let orderStatus = getServiceRequest("/orderstatus/alldata");


    //Retriving data from the data base using ajax common function defined in the coomonFunctions.js
    let vehicleRoute = getServiceRequest("/vehicleroute/alldata");

    //filling data into dropdown
    fillDataIntoSelect(selectDeliveryRoute1, "Please select vehicle Route", vehicleRoute, "name");


    //Update button getsdissapeared when Add Customer Order clicked
    buttonSubmit1.style.display = "block";
    buttonUpdate1.style.display = "none";



}


const refreshForm2 = () => {

    //create a new object for databinding at frontend
    customerorder = new Object();

    //When Individual tab get selected, the value of customer order type of customerorder object is binded
    customerorder.customer_order_type_id = { id: 1, type: "Individual" }

    //Cleaning attributes(Shop Orders)
    formShop.reset();


    //Removing Validation using a common function
    setDefault([selectCustomerName2,
        dateRequiredDateShopCustomer2,
        textTotalPrice2,
        selectSession2,
        dateFromDate,
        dateToDate]);

    //cleaning radio buttons
    justOnceRadio.checked = false;
    recurrentRadio.checked = false;


    //Retriving customername from logged customer
    let customername = getServiceRequest("/customerlogin/customernamebyusername")
    console.log(customername);
    //filling data into dropdown
    fillDataIntoSelect(selectCustomerName2, "Please select Customer Name", customername, "name");

    //Retriving data from the data base using ajax common function defined in the coomonFunctions.js
    let session = getServiceRequest("/productionsession/alldata")

    //filling data into dropdown
    fillDataIntoSelect(selectSession2, "Please select Production session..!", session, "name");

    //Update button getsdissapeared when Add Customer Order clicked
    buttonSubmit2.style.display = "block";
    buttonUpdate2.style.display = "none";

}

//checking errors in the form
const checkFormError1 = () => {

    let errors = "";

    if (customerorder.customer_id == null) {
        errors = errors + "Please Select a valid Customer Name..!\n";
    }

    if (customerorder.total_price == null) {
        errors = errors + "Please Enter a valid Total Price..!\n";
    }

    if (customerorder.required_date == null) {
        errors = errors + "Please Enter the Required Date..!\n";
    }


    if (customerorder.required_time == null) {
        errors = errors + "Please Enter the Required Time..!\n";
    }


    if (customerorder.collection_method_id == null) {
        errors = errors + "Please Select Collection Method..!\n";
    }

    if (deliveryRadio.checked) {

        if (customerorder.required_address == null) {
            errors = errors + "Please Enter Required Address..!\n";
        }
        if (customerorder.vehicle_route_id == null) {
            errors = errors + "Please Select Vehicle Route..!\n";
        }
    }


    return errors;
}


//checking errors in the form
const checkFormError2 = () => {

    let errors = "";

    if (customerorder.customer_id == null) {
        errors = errors + "Please Select a valid Customer Name..!\n";
    }



    if (customerorder.required_date == null) {
        errors = errors + "Please Enter the Required Date..!\n";
    }


    if (customerorder.production_session_id == null) {
        errors = errors + "Please Enter the Order Session..!\n";
    }


    if (customerorder.total_price == null) {
        errors = errors + "Please Enter a valid Total Price..!\n";
    }

    if (customerorder.customer_order_nature_id == null) {
        errors = errors + "Please Enter the Order Nature..!\n";
    }

    if (recurrentRadio.checked) {

        if (customerorder.from_date == null) {
            errors = errors + "Please Enter Starting Date..!\n";
        }
        if (customerorder.to_date == null) {
            errors = errors + "Please Enter End date..!\n";
        }
    }

    return errors;
}


//form submit event function
const buttonOrderSubmit1 = () => {

    //Check form error for required element
    let errors = checkFormError1();

    if (errors == "") {

        let userConfirmMsg1 =
            "\n Customer name:" + customerorder.customer_id.name +
            "\n Total Price:" + customerorder.total_price +
            "\n Required Date :" + customerorder.required_date +
            "\n Required Time:" + customerorder.required_time +
            "\n Collection Method:" + customerorder.collection_method_id.name +
            "\n Customer Order Type:" + customerorder.customer_order_type_id.type;

        if (deliveryRadio.checked) {
            userConfirmMsg1 +=
                "\n Required Address:" + customerorder.required_address +
                "\n Vehicle Route:" + customerorder.vehicle_route_id.name;
        }



        swal({
            title: "Are you sure to Submit Following Details..?",
            text: userConfirmMsg1,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((userResponce) => {

                if (userResponce) {
                    //call post service
                    let postResponce = getHTTPServiceRequest("/onlinecustomerorder/insert", "POST", customerorder);
                    if (postResponce == "OK") {
                        swal("Saved Successfully ....!");


                        refreshForm1();
                        //$("#orderForm").modal("hide");



                    } else {
                        swal("Failed to submit..! \n" + postResponce);

                    }


                }





            })



    } else {

        swal("Form has following errors...\n" + errors);


    }


}


//form submit event function
const buttonOrderSubmit2 = () => {

    //Check form error for required element
    let errors = checkFormError2();

    if (errors == "") {

        let userConfirmMsg2 =
            "\n Customer name:" + customerorder.customer_id.name +
            "\n Required Date :" + customerorder.required_date +
            "\n Customer Order Session:" + customerorder.production_session_id.name +
            "\n Total Price:" + customerorder.total_price +
            "\n Customer Order Nature:" + customerorder.customer_order_nature_id.nature +
            "\n Customer Order Type:" + customerorder.customer_order_type_id.type;


        if (recurrentRadio.checked) {
            userConfirmMsg2 +=
                "\n From:" + customerorder.from_date +
                "\n To:" + customerorder.to_date;
        }



        swal({
            title: "Are you sure to Submit Following Details..?",
            text: userConfirmMsg2,
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((userResponce) => {

                if (userResponce) {
                    //call post service
                    let postResponce = getHTTPServiceRequest("/onlinecustomerorder/insert", "POST", customerorder);
                    if (postResponce == "OK") {
                        swal("Saved Successfully ....!");


                        refreshForm2();
                        //$("#orderForm").modal("hide");



                    } else {
                        swal("Failed to submit..! \n" + postResponce);

                    }


                }





            })


    } else {

        swal("Form has following errors...\n" + errors);


    }




}





