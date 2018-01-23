import $ from 'jquery';

/*########## AJAX CONFIGURATION ##########*/
export const API_URL = "http://localhost:8080/api";

export function ajaxSetup() {
    $.ajaxSetup({
        contentType: "application/json; charset=utf-8",
        crossDomain: true,
        dataType: "json"
    });
}

/*########## ALERTCONTAINER CONFIGURATION ##########*/
export const alertOptions = {
    offset: 40,
    position: 'top right',
    theme: 'light',
    time: 5000,
    transition: 'fade'
};

/*########## ORDERS CONFIGURATION ##########*/
export const takingOrders = true;
export const takingBuildOrders = true;
export const takingDevOrders = true;

/*########## GO TO TOP CONFIGURATION ##########*/
export const goToTopStyle = {
    position: 'fixed',
    bottom: 4,
    right: 20,
    cursor: 'pointer',
    transitionDuration: '0.5s',
    transitionTimingFunction: 'linear',
    transitionDelay: '0.3s',
    color: 'tomato',
};

/*########## MAINTENANCE CONFIGURATION ##########*/
export const maintenanceMode = true;