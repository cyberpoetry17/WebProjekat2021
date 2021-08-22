<template>
    <div>
        <div id="map" class="map"></div>
    </div>
</template>

<script>

module.exports = {
    data() {
        return {
            comments: [
                {
                    username: 'Petar Petrovic',
                    score: 4.5,
                    body: 'Okej restoran, sta znam. Dobra pica 2',
                    status: 'Pending',
                },
                {
                    username: 'Mirko Mirkovic',
                    score: 5.0,
                    body: 'Kul',
                    status: 'Approved',
                },
                {
                    username: 'Petar Mirkovic',
                    score: 1.5,
                    body: 'Fuj!! Hrana grozna1!1 Ne preporucujem uopste, 1.5 samo zbog radnice xexe',
                    status: 'Denied',
                }
            ],
            map: null,
            restaurant: {
                name: "Liman",
                status: 'Open',
                type: 'Pizzeria',
                avgScore: 4.3,
                address: {
                    state: "Serbia",
                    city: "Novi Sad",
                    street: "Sekspirova",
                    streetNo: "12a",
                    lat: 45.2408613,
                    lon: 19.8360667,
                }
            }
        }
    },
    mounted() {
        if(!this.restaurant.articles) {
            this.restaurant.articles = [];
        }
        this.restaurant.articles.forEach(a => {
            a.edit = false;
        });
        this.map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([this.restaurant.address.lon, this.restaurant.address.lat]),
            zoom: 15
            })
        });
        let restPointer = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.fromLonLat([this.restaurant.address.lon, this.restaurant.address.lat]))
        });
        restPointer.setStyle(
            new ol.style.Style({
                image: new ol.style.Icon({
                    crossOrigin: 'anonymous',
                    src: 'img/map-marker-icon.png',
                    scale: 0.02,
                }),
            })
        );
        var layer = new ol.layer.Vector({
        source: new ol.source.Vector({
            features: [ restPointer,
            ]})});
        this.map.addLayer(layer);
    }
};

</script>

<style scoped>
    #rest-main {
        background: #fafafa;
    }

    #rest-container {
        margin: 0 auto;
        width: 80vw;
    }

    #rest-cover {
        height: 40vh;
    }

    #rest-cover img {
        width: 100%;
        height: 100%;
    }

    #rest-info {
        position: relative;
        padding: 20px;
        width: 90%;
        margin: 0 auto;
        margin-top: -20px;
        z-index: 2;
        background: #fff;
    }

    #location {
        width: 80%;
        margin: 0 auto;
    }

    #location h2 {
        color: #666;
        border-bottom: solid 1px #eee;
    }

    #location-container {
        display: grid;
        grid-template-columns: 1fr auto;
        padding: 20px;
    }

    .map {
        width: 400px;
        height: 400px;
    }

    #rest-header {
        display: flex;
        flex-direction: row;
        width: 60%;
        margin: 0 auto;
        margin-bottom: 20px;
    }

    #rest-header b {
        text-transform: uppercase;
    }

    #score-and-buy {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    #rest-score {
        font-size: 3rem;
        font-weight: 500;
    }

    .open {
        color: #2ecc71;
    }

    .closed {
        color: #e74c3c;
    }

    #articles-and-comments {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 50px;
        width: 80%;
        margin: 0 auto;
    }

    #articles h2 {
        color: #666;
        border-bottom: solid 1px #eee;
    }

    .articles {
        height: 400px;
        /* width: 400px; */
        overflow: auto;
    }

    .article {
        display: flex;
        flex-direction: row;
        padding: 5px;
        /* width: 400px; */
        border-bottom: solid 1px #eee;
    }

    .article img {
        width: 128px;
        height: 128px;
    }

    .article-info h3 {
        font-size: 2.5rem;
    }

    #comments h2 {
        color: #666;
        border-bottom: solid 1px #eee;
    }

    .comments {
        height: 400px;
        overflow: auto;
    }

    .comment-container {
        display: flex;
        flex-direction: row;
        /* width: 400px; */
    }

    .comment {
        border-bottom: solid 1px #eee;
        padding: 5px;
    }

    .comment-container img {
        width: 128px;
        height: 128px;
    }

    .comment-info {
        display: flex;
        flex-direction: column;
    }

    .comment-info b {
        text-transform: uppercase;
    }

    .comment-info h3 {
        font-size: 2.5rem;
    }

    .comment-actions {
        display: flex;
        flex-direction: row;
    }

    .approved {
        color: #2ecc71;
    }

    .button-approve {
        color: #fff;
        background-color: #2ecc71;
    }

    .button-deny {
        color: #fff;
        background-color: #e74c3c;
    }

    .pending {
        color: #666;
    }

    .denied {
        color: #e74c3c;
    }

    #review-container {
        display: flex;
        flex-direction: column;
    }

    #review-container h3 {
        margin-top: 20px;
        font-size: 2.5rem;
        color: #666;
    }

    #new-article-container {
        display: flex;
        flex-direction: column;
        padding: 10px;
    }

    #new-article-and-profile {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 10px;
    }

    #new-article {
        display: flex;
        flex-direction: column;
        text-align: left;
    }

    #price-and-type {
        display: flex;
        flex-direction: column;
    }

    #new-article-profile img {
        cursor: pointer;
        width: 128px;
        height: 128px;
    }

    #thumbnail-placholder {
        width: 128px;
        height: 128px;
        cursor: pointer;
        display: grid;
        place-items: center;
        background: #eee;
        font-size: 5rem;
    }    

    .edit-article-pencil {
        color: #666;
        font-size: 2rem;
    }

    .edit-article-actions {
        display: flex;
        flex-direction: row;
        padding: 5px;
    }

</style>