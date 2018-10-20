function initMap() {
    // The location of Uluru
    var uluru = {lat: -25.344, lng: 131.036};
    // The map, centered at Uluru
    var map = new google.maps.Map(
        document.getElementById('map'), {zoom: 4, center: uluru});

    var data = getData();
    drawHospitals(map, data['hospitals']);
    drawAmbulances(map, data['ambulances']);
    drawRoutes(map, data['routes']);
}

function drawHospitals(map, hospitals) {
    var bound = new google.maps.LatLngBounds();
    for(var idx in hospitals) {
        if(hospitals.hasOwnProperty(idx)) {
            var hospital = hospitals[idx];
            var latLng = new google.maps.LatLng(hospital.lat, hospital.long);
            var marker = new google.maps.Marker({position: latLng, map: map});
            bound.extend(latLng);
        }
    }
    map.fitBounds(bound);
}

function drawAmbulances(map, ambulances) {
    for(var idx in ambulances) {
        if(ambulances.hasOwnProperty(idx)) {
            var hospital = ambulances[idx];
            var latLng = new google.maps.LatLng(hospital.lat, hospital.long);
            var marker = new google.maps.Marker({
                position: latLng,
                icon: {
                    fillColor: '#fff',
                    fillOpacity: 1,
                    scale: 0.05,
                    anchor: new google.maps.Point(320, 256),
                    path: 'M624 352h-16V243.9c0-12.7-5.1-24.9-14.1-33.9L494 110.1c-9-9-21.2-14.1-33.9-14.1H416V48c0-26.5-21.5-48-48-48H48C21.5 0 0 21.5 0 48v320c0 26.5 21.5 48 48 48h16c0 53 43 96 96 96s96-43 96-96h128c0 53 43 96 96 96s96-43 96-96h48c8.8 0 16-7.2 16-16v-32c0-8.8-7.2-16-16-16zM160 464c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm144-248c0 4.4-3.6 8-8 8h-56v56c0 4.4-3.6 8-8 8h-48c-4.4 0-8-3.6-8-8v-56h-56c-4.4 0-8-3.6-8-8v-48c0-4.4 3.6-8 8-8h56v-56c0-4.4 3.6-8 8-8h48c4.4 0 8 3.6 8 8v56h56c4.4 0 8 3.6 8 8v48zm176 248c-26.5 0-48-21.5-48-48s21.5-48 48-48 48 21.5 48 48-21.5 48-48 48zm80-208H416V144h44.1l99.9 99.9V256z'
                },
                map: map
            });
        }
    }
}

function drawRoutes(map, routes) {
    for(var idx in routes) {
        if(routes.hasOwnProperty(idx)) {
            var route = routes[idx];
            drawRoute(map, route);
        }
    }
}

function drawRoute(map, route) {
    var points = route['points'];
    var list = [];
    for(var i=0; i < points.length; i++) {
        list.push(new google.maps.LatLng(points[i][0], points[i][1]));
    }
    var path = new google.maps.Polyline({
        path: list,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 3
    });
    path.setMap(map);
}

function getData() {
    return {
        "routes": [{"points": [
            [40.741895, -73.989308],
            [40.3593167,0.36542039999994813],
            [40.4654987,0.17876569999998537]
        ]}],
        "ambulances": [
            {
                "name": "Benicarló",
                "lat": 40.741895,
                "long": -73.989308,
                "type": "bravo"
            },
            {
                "name": "Peñíscola",
                "lat": 40.3593167,
                "long": 0.36542039999994813,
                "type": "bravo"
            },
            {
                "name": "Sant_Mateo",
                "lat": 40.4654987,
                "long": 0.17876569999998537,
                "type": "samu"
            },
            {
                "name": "Vinaròs",
                "lat": 40.4644537,
                "long": 0.4500352000000021,
                "type": "samu"
            },
            {
                "name": "Vinaròs",
                "lat": 40.4644537,
                "long": 0.4500352000000021,
                "type": "bravo"
            },
            {
                "name": "Traiguera",
                "lat": 40.5244341,
                "long": 0.2895942999999761,
                "type": "tna"
            },
            {
                "name": "Albocàsser",
                "lat": 40.356701,
                "long": 0.025070499999969797,
                "type": "bravo"
            },
            {
                "name": "Morella",
                "lat": 40.6181387,
                "long": -0.10154920000002221,
                "type": "bravo"
            },
            {
                "name": "Vilafranca",
                "lat": 42.2784669,
                "long": -1.74536420000004,
                "type": "samu"
            },
            {
                "name": "Castellón",
                "lat": 40.002782,
                "long": -0.04192799999998442,
                "type": "samu"
            },
            {
                "name": "Castellón",
                "lat": 40.002782,
                "long": -0.04192799999998442,
                "type": "helicoptero"
            },
            {
                "name": "Torreblanca",
                "lat": 40.2201804,
                "long": 0.1951318999999785,
                "type": "samu"
            },
            {
                "name": "Segorbe",
                "lat": 39.7952118,
                "long": -0.46910969999998997,
                "type": "samu"
            },
            {
                "name": "Vall_d_Uixò",
                "lat": 39.8242386,
                "long": -0.2315017999999327,
                "type": "samu"
            }
        ],
        "hospitals": [
            {
                "name": "Vinaròs",
                "lat": 40.4644537,
                "long": 0.4500352000000021
            },
            {
                "name": "Sagundo",
                "lat": 39.67387220000001,
                "long": -0.2317180999999664
            },
            {
                "name": "Castellón",
                "lat": 40.002782,
                "long": -0.04192799999998442
            }
        ]
    };
}