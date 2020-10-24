jQuery(document).ready(function($) {

	'use strict';
      
      $('#form-submit .date').datepicker({
      });
    
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
            var postdata = {
                'source' : $("#from-location-select").val(),
                'destination' : $("#to-location-select").val(),
                'departureTime' : new Date($("#departure-time").val()).getTime(),
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
                var trHTML = '<tr>'+
                            '<th>FLIGHT NO</th>'+
                            '<th>FLIGHT NAME</th>'+
                            '<th>SOURCE</th>'+
                            '<th>DESTINATION</th>'+
                            '<th>DEPARTURE TIME</th>'+
                            '<th>ARRIVAL TIME</th>'+
                            '<th>DURATION</th>'+
                            '<th>PRICE</th>'+
                            '<th>STOPS</th>'+
                            '<th>CABIN</th>'+
                            '<th>OFFER</th>'+
                        '</tr>';
                $('#flight-details').empty();
                $.each(data, function (i, item) {
                trHTML += '<tr>'+
                    '<td>' + data[i].id + '</td>'+
                    '<td>' + data[i].flightName + '</td>'+
                    '<td>' + data[i].source + '</td>'+
                    '<td>' + data[i].destination + '</td>'+
                    '<td>' + new Date(data[i].departure) + '</td>'+
                    '<td>' + new Date(data[i].arrival) + '</td>'+
                    '<td>' + data[i].duration + '</td>'+
                    '<td>' + data[i].price + '</td>'+
                    '<td>' + data[i].stops + '</td>'+
                    '<td>' + data[i].cabin + '</td>'+
                    '<td>' + data[i].offercode + '</td>'+
                    '</tr>';
                });
                $('#flight-details').append(trHTML);
            });
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
    
    function onFromLocationSelect(){
        console.log("Hai from source");
    }
    
    function onToLocationSelect(){
         console.log("Hai from destination");
    }

        $(".pop-button").click(function () {
            $(".pop").fadeIn(300);
            
        });

        $(".pop > span").click(function () {
            $(".pop").fadeOut(300);
        });


        $(window).on("scroll", function() {
            if($(window).scrollTop() > 100) {
                $(".header").addClass("active");
            } else {
                //remove the background property so it comes transparent again (defined in your css)
               $(".header").removeClass("active");
            }
        });


	/************** Mixitup (Filter Projects) *********************/
    	$('.projects-holder').mixitup({
            effects: ['fade','grayscale'],
            easing: 'snap',
            transitionSpeed: 400
        });


     init();
});
