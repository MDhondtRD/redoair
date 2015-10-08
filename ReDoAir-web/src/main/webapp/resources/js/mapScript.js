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

//-----------------------------------------------------------------------------START OF MARKER-------------------------------------------------------
var iconStyle = new ol.style.Style({
    image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
        anchor: [0.5, 46],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: '../data/map_icon.png'
    }))
});

var vectorSource = new ol.source.Vector({
});

var mapMarkers = [];

function createMapMarker(latitude, longitude, destinationName) {;
    var coordinate = [latitude, longitude];
    var convertedCoordinates = ol.proj.transform(coordinate, 'EPSG:4326', 'EPSG:3857');
    coordinate = [convertedCoordinates[0], convertedCoordinates[1]];//array holding long, lat values : 50.970319, 4.515209
    var point = new ol.geom.Point([coordinate[0], coordinate[1]]);//array holding those very same values.
    var iconFeature = new ol.Feature({
        geometry: point,
        id: 'COUNTRY',
        name: destinationName
    });
    iconFeature.setStyle(iconStyle);
    mapMarkers.push(iconFeature);
}

function addMapMarkersToMap() {
    vectorSource.addFeatures(mapMarkers);
}

var markerLayer = new ol.layer.Vector({
    source: vectorSource
});
//-----------------------------------------------------------------------------END OF MARKER-------------------------------------------------------
var vectorLayer = new ol.layer.Vector({
    source: new ol.source.Vector({
        url:'../scripts/countries.geojson',
        format: new ol.format.GeoJSON(),
        wrapX: false
    }),
    style: function(feature, resolution) {
        style.getText().setText(resolution < 5000 ? feature.get('name') : '');
        return styles;
    }
});

var map = new ol.Map({
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM({layer: 'sat', wrapX: false
            })
        }),
        vectorLayer,
        markerLayer
    ],
    target: 'map',
    view: new ol.View({
        center: [0, 0],
        zoom: 1,
        maxZoom: 16
    })
});


var highlightStyleCache = {};

var featureOverlay = new ol.FeatureOverlay({
    map: map,
    style: function(feature, resolution) {
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
var displayFeatureInfo = function(pixel) {
    var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
        return feature;
    });

    var info = document.getElementById('info');
    if (feature) {
        info.innerHTML = feature.getId() + ': ' + feature.get('name');
        console.log("ID: " + feature.getId() + " , COUNTRYCODE: " + feature.get('name'));
    } else {
        info.innerHTML = '&nbsp;';
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


map.on('pointermove', function(evt) {
    if (evt.dragging) {
        return;
    }
    var pixel = map.getEventPixel(evt.originalEvent);
});

map.on('click', function(evt) {
    var pixel = map.getEventPixel(evt.originalEvent);
    var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
        return feature;
    });
    retrieveDestinationsForClickedCountry(feature.getId());
    createMapMarker(4.486885983517447,50.90131747057211);//first latitude then longitude
    addMapMarkersToMap();
    displayFeatureInfo(evt.pixel);
});


function mapPinOnZaventemAirport() {
    var coordinate =[500973.12345271517, 6605441.104038407];//Coordinate of Zaventem airport in the map's current metric
    var pixel = map.getPixelFromCoordinate(coordinate);
    console.log("MAP-PIN ON ZAVENTEM: " + pixel);
    console.log("PIXEL COORDS: " + pixel[0] + " AND " + pixel[1]);
}

function retrieveDestinationsForClickedCountry(countryCode) {
    document.getElementById('hiddenForm:countryCode').setAttribute("value", countryCode);
    document.getElementById('hiddenForm:invisibleClickTarget').click();
}

function readDestinations() {
    var listOfDestination = document.getElementById('otherHiddenForm:listOfDestinations');
    console.log(listOfDestination);
}