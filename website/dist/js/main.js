const INCIDENCE_COLORS = {
    1: 'red',
    2: 'orange',
    3: 'yellow',
    4: 'green',
    5: 'blue'
};

const HOSPITAL_PATH = 'M448 492v20H0v-20c0-6.627 5.373-12 12-12h20V120c0-13.255 10.745-24 24-24h88V24c0-13.255 10.745-24 24-24h112c13.255 0 24 10.745 24 24v72h88c13.255 0 24 10.745 24 24v360h20c6.627 0 12 5.373 12 12zM308 192h-40c-6.627 0-12 5.373-12 12v40c0 6.627 5.373 12 12 12h40c6.627 0 12-5.373 12-12v-40c0-6.627-5.373-12-12-12zm-168 64h40c6.627 0 12-5.373 12-12v-40c0-6.627-5.373-12-12-12h-40c-6.627 0-12 5.373-12 12v40c0 6.627 5.373 12 12 12zm104 128h-40c-6.627 0-12 5.373-12 12v84h64v-84c0-6.627-5.373-12-12-12zm64-96h-40c-6.627 0-12 5.373-12 12v40c0 6.627 5.373 12 12 12h40c6.627 0 12-5.373 12-12v-40c0-6.627-5.373-12-12-12zm-116 12c0-6.627-5.373-12-12-12h-40c-6.627 0-12 5.373-12 12v40c0 6.627 5.373 12 12 12h40c6.627 0 12-5.373 12-12v-40zM182 96h26v26a6 6 0 0 0 6 6h20a6 6 0 0 0 6-6V96h26a6 6 0 0 0 6-6V70a6 6 0 0 0-6-6h-26V38a6 6 0 0 0-6-6h-20a6 6 0 0 0-6 6v26h-26a6 6 0 0 0-6 6v20a6 6 0 0 0 6 6z';
const MEDICAL_CENTER_PATH = 'M128 244v-40c0-6.627 5.373-12 12-12h40c6.627 0 12 5.373 12 12v40c0 6.627-5.373 12-12 12h-40c-6.627 0-12-5.373-12-12zm140 12h40c6.627 0 12-5.373 12-12v-40c0-6.627-5.373-12-12-12h-40c-6.627 0-12 5.373-12 12v40c0 6.627 5.373 12 12 12zm-76 84v-40c0-6.627-5.373-12-12-12h-40c-6.627 0-12 5.373-12 12v40c0 6.627 5.373 12 12 12h40c6.627 0 12-5.373 12-12zm76 12h40c6.627 0 12-5.373 12-12v-40c0-6.627-5.373-12-12-12h-40c-6.627 0-12 5.373-12 12v40c0 6.627 5.373 12 12 12zm180 124v36H0v-36c0-6.627 5.373-12 12-12h19.5V85.035C31.5 73.418 42.245 64 55.5 64H144V24c0-13.255 10.745-24 24-24h112c13.255 0 24 10.745 24 24v40h88.5c13.255 0 24 9.418 24 21.035V464H436c6.627 0 12 5.373 12 12zM79.5 463H192v-67c0-6.627 5.373-12 12-12h40c6.627 0 12 5.373 12 12v67h112.5V112H304v24c0 13.255-10.745 24-24 24H168c-13.255 0-24-10.745-24-24v-24H79.5v351zM266 64h-26V38a6 6 0 0 0-6-6h-20a6 6 0 0 0-6 6v26h-26a6 6 0 0 0-6 6v20a6 6 0 0 0 6 6h26v26a6 6 0 0 0 6 6h20a6 6 0 0 0 6-6V96h26a6 6 0 0 0 6-6V70a6 6 0 0 0-6-6z';

let infoWindow = null;
let map = null;
let tempMark = null;
let incidences = [];
let incidenceMarkers = [];

function initMap() {
    TxtOverlay.prototype = new google.maps.OverlayView();

    TxtOverlay.prototype.onAdd = function() {
        // Note: an overlay's receipt of onAdd() indicates that
        // the map's panes are now available for attaching
        // the overlay to the map via the DOM.

        // Create the DIV and set some basic attributes.
        var div = document.createElement('DIV');
        div.className = this.cls_;

        div.innerHTML = this.txt_;

        // Set the overlay's div_ property to this DIV
        this.div_ = div;
        var overlayProjection = this.getProjection();
        var position = overlayProjection.fromLatLngToDivPixel(this.pos);
        div.style.left = position.x + 'px';
        div.style.top = position.y + 'px';
        // We add an overlay to a map via one of the map's panes.

        var panes = this.getPanes();
        panes.floatPane.appendChild(div);
    };

    TxtOverlay.prototype.draw = function() {
        var overlayProjection = this.getProjection();

        // Retrieve the southwest and northeast coordinates of this overlay
        // in latlngs and convert them to pixels coordinates.
        // We'll use these coordinates to resize the DIV.
        var position = overlayProjection.fromLatLngToDivPixel(this.pos);


        var div = this.div_;
        div.style.left = position.x + 'px';
        div.style.top = position.y + 'px';
    };

    //Optional: helper methods for removing and toggling the text overlay.
    TxtOverlay.prototype.onRemove = function() {
        this.div_.parentNode.removeChild(this.div_);
        this.div_ = null;
    };

    TxtOverlay.prototype.hide = function() {
        if (this.div_) {
            this.div_.style.visibility = "hidden";
        }
    };

    TxtOverlay.prototype.show = function() {
        if (this.div_) {
            this.div_.style.visibility = "visible";
        }
    };

    TxtOverlay.prototype.toggle = function() {
        if (this.div_) {
            if (this.div_.style.visibility === "hidden") {
                this.show();
            } else {
                this.hide();
            }
        }
    };

    TxtOverlay.prototype.toggleDOM = function() {
        if (this.getMap()) {
            this.setMap(null);
        } else {
            this.setMap(this.map_);
        }
    };

    // The map, centered at Uluru
    map = new google.maps.Map(
        document.getElementById('map'), {zoom: 4, center: {lat: 0, lng: 0},
            mapTypeId: google.maps.MapTypeId.ROADMAP});
    infoWindow = new google.maps.InfoWindow();
    infoWindow.setMap(map);

    $('#submitIncidences')
        .removeAttr("hidden")
        .click(()=>{
            $('#submitIncidences').remove();
            google.maps.event.clearListeners(map, 'click');
            drawData(getData());
        });

    map.addListener('click', (event) => {
        if(tempMark) {
            tempMark.setMap(null);
            tempMark = null;
        }

        tempMark = new google.maps.Marker({position: event.latLng, map: map});
        let content = $(`<div>Impact: <input type="number" min=1 max=5 value=5><button class="btn btn-secondary btn-sm m-1">Save</button></div>`);
        infoWindow.setContent(content[0]);
        content.find("button").click(()=>{
            let incidence =  {
                position: {lat: event.latLng.lat(), lng: event.latLng.lng()},
                gravity: content.find("input").val()};
            incidences.push(incidence);
            google.maps.event.clearListeners(infoWindow, 'closeclick');
            infoWindow.close();
            incidenceMarkers.push(tempMark);
            tempMark = null;
        });
        infoWindow.addListener('closeclick', ()=>{
            tempMark.setMap(null);
            tempMark = null;
            google.maps.event.clearListeners(infoWindow, 'closeclick');
        });

        infoWindow.open(map, tempMark);
    });
}

function drawData(data) {
    let bound = new google.maps.LatLngBounds();
    drawCenters(data, bound, infoWindow);
    drawIncidences(data, bound, infoWindow);
    drawRoutes(data);
    map.fitBounds(bound);
}

function drawCenters(data, bound, info) {
    for(let [id, center] of Object.entries(data.centers)) {
        let marker = new google.maps.Marker({
            position: center.position,
            icon: {
                fillColor: '#fff',
                fillOpacity: 1,
                stroke: "black",
                strokeWeight: 2,
                scale: 0.07,
                zIndex: 10,
                anchor: new google.maps.Point(320, 256),
                path: (center.type === 'medical_center' ?
                    MEDICAL_CENTER_PATH
                    : HOSPITAL_PATH)
            },
            map: map});
        marker.addListener('click', ()=>{
            info.setContent(`Code: <b>${id}</b>`);
            info.open(map, marker);
        });
        bound.extend(center.position);
    }
}

function drawIncidences(data, bound, info) {
    for(let [id, incidence] of Object.entries(data.incidences)) {
        let marker = new google.maps.Marker({
            position: incidence.position,
            icon: {
                fillColor: INCIDENCE_COLORS[incidence.gravity],
                fillOpacity: 1,
                scale: 9,
                strokeWeight: 2,
                zIndex: 30,
                anchor: new google.maps.Point(0,0),
                path: google.maps.SymbolPath.CIRCLE
            },
            map: map});
        marker.addListener('click', ()=>{
            info.setContent(`Code: <b>${id}</b><br>Impact: <b style="color: ${INCIDENCE_COLORS[incidence.gravity]}">${incidence.gravity}</b>`);
            info.open(map, marker);
        });
        bound.extend(incidence.position);
    }
}

function drawVehicle(position, vehicleId, vehicle) {
    var marker = new google.maps.Marker({
        position: position,
        icon: {
            fillColor: '#fff',
            fillOpacity: 1,
            scale: 0.05,
            anchor: new google.maps.Point(320, 256),
            path: (vehicle === 'helicopter' ?
                'M304 384h272c17.67 0 32-14.33 32-32 0-123.71-100.29-224-224-224V64h176c8.84 0 16-7.16 16-16V16c0-8.84-7.16-16-16-16H144c-8.84 0-16 7.16-16 16v32c0 8.84 7.16 16 16 16h176v64H112L68.8 70.4C65.78 66.37 61.03 64 56 64H16.01C5.6 64-2.04 73.78.49 83.88L32 192l160 64 86.4 115.2A31.992 31.992 0 0 0 304 384zm112-188.49C478.55 208.3 528.03 257.44 540.79 320H416V195.51zm219.37 263.3l-22.15-22.2c-6.25-6.26-16.24-6.1-22.64.01-7.09 6.77-13.84 11.25-24.64 11.25H240c-8.84 0-16 7.18-16 16.03v32.06c0 8.85 7.16 16.03 16 16.03h325.94c14.88 0 35.3-.47 68.45-29.52 7.02-6.14 7.57-17.05.98-23.66z'
                : 'M624 352h-16V243.9c0-12.7-5.1-24.9-14.1-33.9L494 110.1c-9-9-21.2-14.1-33.9-14.1H416V48c0-26.5-21.5-48-48-48H48C21.5 0 0 21.5 0 48v320c0 26.5 21.5 48 48 48h16c0 53 43 96 96 96s96-43 96-96h128c0 53 43 96 96 96s96-43 96-96h48c8.8 0 16-7.2 16-16v-32c0-8.8-7.2-16-16-16zM160 464c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm144-248c0 4.4-3.6 8-8 8h-56v56c0 4.4-3.6 8-8 8h-48c-4.4 0-8-3.6-8-8v-56h-56c-4.4 0-8-3.6-8-8v-48c0-4.4 3.6-8 8-8h56v-56c0-4.4 3.6-8 8-8h48c4.4 0 8 3.6 8 8v56h56c4.4 0 8 3.6 8 8v48zm176 248c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm80-208H416V144h44.1l99.9 99.9V256z')
        },
        zIndex: 20,
        map: map
    });
    var txt = new TxtOverlay(new google.maps.LatLng(position), '<b>'+vehicleId+'</b>', 'customBox', map);
}

/**
 * @param map
 * @param {ApiInterface} data
 */
function drawRoutes(data) {
    for(let route of data.routes) {
        drawRoute(data, route);
        drawVehicle(
            route.origin,
            route.vehicle,
            data.vehicles[route.vehicle]);
    }
}

/**
 *
 * @param {ApiInterface} data
 * @param {RouteInterface} route
 */
function drawRoute(data, route) {
    var path = new google.maps.Polyline({
        path: [
            route.origin,
            data.incidences[route.incidence].position,
            route.destination
        ],
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 3,
        zIndex: 5
    });
    path.setMap(map);
}

//adapded from this example http://code.google.com/apis/maps/documentation/javascript/overlays.html#CustomOverlays
//text overlays
function TxtOverlay(pos, txt, cls, map) {

    // Now initialize all properties.
    this.pos = pos;
    this.txt_ = txt;
    this.cls_ = cls;
    this.map_ = map;

    // We define a property to hold the image's
    // div. We'll actually create this div
    // upon receipt of the add() method so we'll
    // leave it null for now.
    this.div_ = null;

    // Explicitly call setMap() on this overlay
    this.setMap(map);
}

/**
 * @return {ApiInterface}
 */
function getData() {
    return {
        "vehicles": {
            "XR4FM": "bravo",
            "XJ7OP": "bravo",
            "JO9PK": "samu"
        },
        "centers": {
            "CENT01":{
                position: {
                    lat: 40.3593167,
                    lng: 0.36542039999994813},
                type: "hospital",
                totalBeds: 20,
                freeBeds: 10
            },
            "CENT02":{
                position: {
                    lat: 40.4644537,
                    lng: 0.4500352000000021},
                type: "hospital",
                totalBeds: 20,
                freeBeds: 10
            }
        },
        "incidences": {
            "INC01": {
                position: {
                    lat: 40.4654987,
                    lng: 0.17876569999998537},
                gravity: 4
            }
        },
        "routes": [
            {origin:  {
                lat: 40.3593167, lng: 0.36542039999994813}, incidence: "INC01", destination: {lat: 40.4644537,
                lng: 0.4500352000000021}, vehicle: "JO9PK", kms: 2.4}
        ]
    };
}