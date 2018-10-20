const INCIDENCE_COLORS = {
    1: 'red',
    2: 'orange',
    3: 'yellow',
    4: 'green',
    5: 'blue'
};

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
        let marker = new google.maps.Marker({position: center.position, map: map});
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
            path: 'M624 352h-16V243.9c0-12.7-5.1-24.9-14.1-33.9L494 110.1c-9-9-21.2-14.1-33.9-14.1H416V48c0-26.5-21.5-48-48-48H48C21.5 0 0 21.5 0 48v320c0 26.5 21.5 48 48 48h16c0 53 43 96 96 96s96-43 96-96h128c0 53 43 96 96 96s96-43 96-96h48c8.8 0 16-7.2 16-16v-32c0-8.8-7.2-16-16-16zM160 464c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm144-248c0 4.4-3.6 8-8 8h-56v56c0 4.4-3.6 8-8 8h-48c-4.4 0-8-3.6-8-8v-56h-56c-4.4 0-8-3.6-8-8v-48c0-4.4 3.6-8 8-8h56v-56c0-4.4 3.6-8 8-8h48c4.4 0 8 3.6 8 8v56h56c4.4 0 8 3.6 8 8v48zm176 248c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm80-208H416V144h44.1l99.9 99.9V256z'
        },
        zIndex: 10,
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
        strokeWeight: 3
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