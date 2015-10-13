var style = new ol.style.Style({
    fill: new ol.style.Fill({
        color: 'rgba(255, 255, 255, 0.6)'
    }),
    stroke: new ol.style.Stroke({
        color: '#319FD3',
        width: 1
    }),
    text: new ol.style.Text({
        font: '12px Calibri,sans-serif',
        fill: new ol.style.Fill({
            color: '#000'
        }),
        stroke: new ol.style.Stroke({
            color: '#fff',
            width: 3
        })
    })
});
var styles = [style];
var maxExtent = [-18680000, -2670000, 19120000, 10190000];
var viewExtent = [-20013216.422474474, -20044220.06907162, 20002981.712778978, 20039791.065600768];

var view = new ol.View({
    center: [0, 0],
    maxResolution: 19500,
    minResolution: 100,
    zoom: 1
});

var constrainPan = function () {
    var visible = view.calculateExtent(map.getSize());
    var centre = view.getCenter();
    var delta;
    var adjust = false;
    if ((delta = viewExtent[0] - visible[0]) > 0) {
        adjust = true;
        centre[0] += delta;
    } else if ((delta = viewExtent[2] - visible[2]) < 0) {
        adjust = true;
        centre[0] += delta;
    }
    if ((delta = viewExtent[1] - visible[1]) > 0) {
        adjust = true;
        centre[1] += delta;
    } else if ((delta = viewExtent[3] - visible[3]) < 0) {
        adjust = true;
        centre[1] += delta;
    }
    if (adjust) {
        view.setCenter(centre);
    }
};
view.on('change:resolution', constrainPan);
view.on('change:center', constrainPan);
//-----------------------------------------------------------------------------START OF MARKER-------------------------------------------------------
var iconStyle = new ol.style.Style({
    image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        anchor: [0.5, 46],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: '../../resources/images/map_icon.png'
    }))
});

var vectorSource = new ol.source.Vector({
    wrapX: false
});

var mapMarkers = [];

function createMapMarker(latitude, longitude, destinationName) {
    var coordinate = [latitude, longitude];
    var convertedCoordinates = ol.proj.transform(coordinate, 'EPSG:4326', 'EPSG:3857');
    coordinate = [convertedCoordinates[0], convertedCoordinates[1]];//array holding long, lat values : 50.970319, 4.515209
    var point = new ol.geom.Point([coordinate[0], coordinate[1]]);//array holding those very same values.
    var iconFeature = new ol.Feature({
        geometry: point,
        id: 'COUNTRY',
        name: destinationName
    });
    iconFeature.setId("MAPMARKER");
    iconFeature.setStyle(iconStyle);
    mapMarkers.push(iconFeature);
}

function addMapMarkersToMap() {
    vectorSource.addFeatures(mapMarkers);
}

var markerLayer = new ol.layer.Vector({
    source: vectorSource,
    extent: maxExtent
});
//-----------------------------------------------------------------------------END OF MARKER-------------------------------------------------------
var vectorLayer = new ol.layer.Vector({
    source: new ol.source.Vector({
        url: '../../resources/data/countries.geojson',
        format: new ol.format.GeoJSON(),
        wrapX: false
    }),
    style: function (feature, resolution) {
        style.getText().setText(resolution < 5000 ? feature.get('name') : '');
        return styles;
    },
    extent: maxExtent
});

var mapSourceLayer = new ol.layer.Tile({
    source: new ol.source.OSM({
        layer: 'sat', wrapX: false
    }),
});


var map = new ol.Map({
    controls: ol.control.defaults({attribution: false, rotate: false})
        .extend([new ol.control.FullScreen()]),
    interactions: ol.interaction.defaults({doubleClickZoom: false, altShiftDragRotate: false}),
    layers: [mapSourceLayer,
        vectorLayer,
        markerLayer
    ],
    target: 'map',
    view: view
});


var highlightStyleCache = {};

var featureOverlay = new ol.FeatureOverlay({
    map: map,
    style: function (feature, resolution) {
        var text = resolution < 5000 ? feature.get('name') : '';
        if (!highlightStyleCache[text]) {
            highlightStyleCache[text] = [new ol.style.Style({
                stroke: new ol.style.Stroke({
                    color: '#f00',
                    width: 1
                }),
                fill: new ol.style.Fill({
                    color: 'rgba(255,0,0,0.1)'
                }),
                text: new ol.style.Text({
                    font: '12px Calibri,sans-serif',
                    text: text,
                    fill: new ol.style.Fill({
                        color: '#000'
                    }),
                    stroke: new ol.style.Stroke({
                        color: '#f00',
                        width: 3
                    })
                })
            })];
        }
        return highlightStyleCache[text];
    }
});

var highlight;
var displayFeatureInfo = function (pixel) {
    var feature = map.forEachFeatureAtPixel(pixel, function (feature, layer) {
        return feature;
    });

    //var info = document.getElementById('info');
    //if (feature) {
    //    info.innerHTML = feature.getId() + ': ' + feature.get('name');
    //    console.log("ID: " + feature.getId() + " , COUNTRY: " + feature.get('name'));
    //} else {
    //    info.innerHTML = '&nbsp;';
    //}

    if (feature) {
        console.log("ID: " + feature.getId() + " , COUNTRY: " + feature.get('name'));
    }
    if (feature !== highlight) {
        if (highlight) {
            featureOverlay.removeFeature(highlight);
        }
        if (feature) {
            featureOverlay.addFeature(feature);
        }
        highlight = feature;
    }
};


map.on('pointermove', function (evt) {
    if (evt.dragging) {
        $(element).popover('destroy');
        return;
    }
    var pixel = map.getEventPixel(evt.originalEvent);
});

map.on('click', function (evt) {
    var pixel = map.getEventPixel(evt.originalEvent);
    console.log(pixel);
    console.log(map.getCoordinateFromPixel(pixel));
    var feature = map.forEachFeatureAtPixel(pixel, function (feature, layer) {
        return feature;
    });
    if (feature.getId() === "MAPMARKER") {
        var geometry = feature.getGeometry();
        var coord = geometry.getCoordinates();
        popup.setPosition(coord);
        $(element).popover({
            'placement': 'top',
            'html': true,
            'content': feature.get('name')
        });
        $(element).popover('show');
    }
    else if(feature.getId() !== "MAPMARKER" && feature.getId() !== undefined) {
        retrieveDestinationsForClickedCountry(feature.get('name'));
        createMapMarker(50, 50, "BORAT");
        addMapMarkersToMap();
        $(element).popover('destroy');
    } else {
        $(element).popover('destroy');
    }
    displayFeatureInfo(evt.pixel);
});


function retrieveDestinationsForClickedCountry(country) {
    document.getElementById('hiddenForm:country').setAttribute("value", country);
    document.getElementById('hiddenForm:invisibleClickTarget').click();
}

function readDestinations() {
    var listOfDestination = document.getElementById('otherHiddenForm:listOfDestinations');
    var index;
    var destination
    for (index = 0; index < listOfDestination.length; index++) {
        destination = listOfDestination[index];
        createMapMarker(destination[0], destination[1], destination[2]);
    }
    addMapMarkersToMap()
    console.log(listOfDestination);
}

function retrieveTripsForClickedDestination(destination) {
    //TO DO: Element names have to be changed and added to the view
    document.getElementById('hiddenForm:destination').setAttribute("value", destination);
    document.getElementById('hiddenForm:invisibleClickTarget').click();
}

//----------------------------START OF POP UP ON MARKER CLICK-----------------------------------------------------------------
var element = document.getElementById('popup');
var popup = new ol.Overlay({
    element: element,
    positioning: 'bottom-center',
    stopEvent: false
});
map.addOverlay(popup);
//----------------------------END OF POP UP ON MARKER CLICK-----------------------------------------------------------------