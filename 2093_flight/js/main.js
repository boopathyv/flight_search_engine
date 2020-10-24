jQuery(document).ready(function($) {

	'use strict';
    
     $("#form-submit").submit(function (event) {
            event.preventDefault();
            searchFlights();
     });
    
     $('.stop_button').click(function (event) {
            searchFlights();
     });
    
     $('#duration-sort-select').change(function (event) {
            searchFlights();
     });
    
     $('#price-sort-select').change(function (event) {
            searchFlights();
     });
    
     $('#airline-select').change(function (event) {
            searchFlights();
     });
    
     $('#class-select').change(function (event) {
            searchFlights();
     });
    
     $('#coupon-select').change(function (event) {
            searchFlights();
     });
    
    function searchFlights(){
            let departureDateTime = new Date($("#departure-time").val());
            let departureDate = departureDateTime.getDate();
            let departureTime = departureDateTime.getTime();
            var postdata = {
                'source' : $("#from-location-select").val(),
                'destination' : $("#to-location-select").val(),
                'departureTime' : departureTime,
                'duration' : $("#duration-sort-select").val(),
                'stops': $("input[name='stops']:checked").val(),
                'price': $("#price-sort-select").val(),
                'flightName' : $("#airline-select").val(),
                'cabin' : $("#class-select").val(),
                'offercode' : $("#coupon-select").val()
            }
            $.post("http://localhost:8080/flight/search/fetchflight", postdata,
                   function(data, status){
                console.log("flight data: " + data);
                loadFlightDetails(data, departureDate);
            });
    }
    
    function loadFlightDetails(data, departureDate){
        data = filterDataByDate(data, departureDate);
        var trHTML = '';
        $('#flight_detail_list').empty();
        if(data.length !== 0){
            $("#flight_detail_list").css("display", "block");
            $("#flight_detail_list").css("padding-left", "0");
                    
            trHTML += '<div class="flight_card_details flight_card_details_header"><div class="sub_flight_card_header"><h4 class="text_bold">AIRLINES</h4></div><div class="sub_flight_card_header"><h4 class="text_bold">DEPART</h4></div><div class="sub_flight_card_header"><h4 class="text_bold">DURATION/STOPS</h4></div><div class="sub_flight_card_header"><h4 class="text_bold">ARRIVE</h4></div><div class="sub_flight_card_header"><h4 class="text_bold">PRICE</h4></div><div class="sub_flight_card_header"><h4 class="text_bold">CABIN</h4></div></div>';
                    
            $.each(data, function (i, item) {                    
                let a = new Date(data[i].departure).toTimeString();
                let departure = a.substr(a.indexOf(':')-2,a.lastIndexOf(':'));
                a = new Date(data[i].arrival).toTimeString();
                let arrival = a.substr(a.indexOf(':')-2,a.lastIndexOf(':'));
                let stop = '';
                switch(data[i].stops){
                    case 0: stop='Non-Stop';break;
                    case 1: stop='1-Stop';break;
                    default: stop= data[i].stops+' Stops';
                }
                trHTML += '<div class="flight_card_details">'+
                    '<div class="sub_flight_card">'+
                    '<h4 class="text_bold">' + data[i].flightName + '</h4>'+
                    '<h4>' + data[i].id + '</h4>'+
                    '</div>'+
                    '<div class="sub_flight_card">'+
                    '<h4 class="text_bold">' + data[i].source + '</h4>'+
                    '<h4 >' + departure + '</h4>'+
                    '</div>'+
                    '<div class="sub_flight_card">'+
                    '<h4 class="text_bold">' + data[i].duration + ' hrs</h4>'+
                    '<h4 >' + stop + '</h4>'+
                    '</div>'+
                    '<div class="sub_flight_card">'+
                    '<h4 class="text_bold">' + data[i].destination + '</h4>'+
                    '<h4 >' + arrival + '</h4>'+
                    '</div>'+
                    '<div class="sub_flight_card">'+
                    '<h2 class="text_bold">Rs. ' + data[i].price + '/-</h2>'+
                    '</div>'+
                    '<div class="sub_flight_card">'+
                    '<h4 class="text_bold">' + data[i].cabin + '</h4>'+
                    '<h4 >' + data[i].offercode + '</h4>'+
                    '</div>'+
                    '</div>';
            });
        }else{
            trHTML += '<div class="logo" style="align-self: center;">'+
                '<img src="img/logo.png" alt="Flight Template"><h1>NO FLIGHTS FOUND...'+ '</h1></div>';
            $("#flight_detail_list").css("display", "flex");
            $("#flight_detail_list").css("padding-left", "25%");
        }
        $('#flight_detail_list').append(trHTML);
    }
    
    function filterDataByDate(data, departureDate){
        var newData = [];
        $.each(data, function (i, item) {
            let date = new Date(data[i].departure).getDate();
            if(departureDate === date){
                newData.push(item);
            }
        });
        return newData;
    }
    
    function init(){
       $.get("http://localhost:8080/flight/search/csvinsert", function(data, status){
           console.log(data);
           loadAllSources();
           loadAllDestinations();
           loadAllAirlines();
           loadAllCabins();
	       loadAllOfferCodes();
       });
    }
    
    function loadAllSources(){
       $.get("http://localhost:8080/flight/search/getAllSources", function(data, status){
            console.log("sources :",data);
            for (var i = 0; i < data.length; i++) {
               $('#from-location-select').append('<option value="' + data[i] + '">' + data[i] + '</option>');
            }
       });
    }
    
    function loadAllDestinations(){
       $.get("http://localhost:8080/flight/search/getAllDestinations", function(data, status){
            console.log("destinations :",data);
            for (var i = 0; i < data.length; i++) {
               $('#to-location-select').append('<option value="' + data[i] + '">' + data[i] + '</option>');
            }
       });
    }
    
    function loadAllAirlines(){
       $.get("http://localhost:8080/flight/search/getAirlines", function(data, status){
            console.log("airlines :",data);
            for (var i = 0; i < data.length; i++) {
               $('#airline-select').append('<option value="' + data[i] + '">' + data[i] + '</option>');
            }
       });
    }
    
    function loadAllCabins(){
       $.get("http://localhost:8080/flight/search/getCabins", function(data, status){
            console.log("cabins :",data);
            for (var i = 0; i < data.length; i++) {
               $('#class-select').append('<option value="' + data[i] + '">' + data[i] + '</option>');
            }
       });
    }
    
    function loadAllOfferCodes(){
       $.get("http://localhost:8080/flight/search/getOfferCode", function(data, status){
            console.log("offer codes :",data);
            for (var i = 0; i < data.length; i++) {
               $('#coupon-select').append('<option value="' + data[i] + '">' + data[i] + '</option>');
            }
       });
    }

    init();
});
