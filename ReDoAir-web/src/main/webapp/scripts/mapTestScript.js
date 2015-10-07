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

function createMapMarker(longitude, latitude, destinationName) {
    var iconFeature = new ol.Feature({
        labelPoint: new ol.geom.Point([longitude, latitude]),
        id: 'COUNTRY',
        name: destinationName
    });
    iconFeature.setStyle(iconStyle);
    mapMarkers.push(iconFeature);
    iconFeature.setGeometryName('labelPoint');
}




function addMapMarkersToMap() {
    vectorSource.addFeatures(mapMarkers);
}

var vectorLayer = new ol.layer.Vector({
    source: vectorSource
});

var rasterLayer = new ol.layer.Tile({
    source: new ol.source.TileJSON({
        url: 'http://api.tiles.mapbox.com/v3/mapbox.geography-class.jsonp',
        crossOrigin: ''
    })
});

var map = new ol.Map({
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM({layer: 'sat'})
        }),
        vectorLayer,
     ],
    target: document.getElementById('map'),
    view: new ol.View({
        center: [0, 0],
        zoom: 15,
    })
});

var element = document.getElementById('popup');

var popup = new ol.Overlay({
    element: element,
    positioning: 'bottom-center',
    stopEvent: false
});
map.addOverlay(popup);

// display popup on click
map.on('click', function(evt) {
    var pixel = map.getEventPixel(evt.originalEvent);
    createMapMarker(50,50,"Blabla");
    createMapMarker(479413.0414046189, 6594375.304218726, "ZAVENTEM");
    addMapMarkersToMap();
    var feature = map.forEachFeatureAtPixel(evt.pixel,
        function(feature, layer) {
            return feature;
        });
    if (feature) {
        if(feature.get('id') === "COUNTRY") {
            console.log("ID: " + feature.getId() + " , COUNTRYCODE: " + feature.get('name'));
        }
        popup.setPosition(evt.coordinate);
        $(element).popover({
            'placement': 'top',
            'html': true,
            'content': feature.get('name')
        });
        $(element).popover('show');
    } else {
        $(element).popover('destroy');
    }
});

// change mouse cursor when over marker
map.on('pointermove', function(e) {
    if (e.dragging) {
        $(element).popover('destroy');
        return;
    }
    var pixel = map.getEventPixel(e.originalEvent);
    var hit = map.hasFeatureAtPixel(pixel);
    map.getTarget().style.cursor = hit ? 'pointer' : '';
});
