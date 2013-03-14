require(['jquery', 'jquery.bootstrap', 'backbone', 'mustache', 'marionette', 'app', 'router', 'coffee'], function($, jQueryBootstrap, Backbone, Mustache, Marionette, App, Router, Coffee) {

    Backbone.Marionette.Renderer.render = Mustache.to_html;

    App.addRegions({
        page: "body"
    });

    App.addInitializer(function() {
        Router.initialize();
        Backbone.history.start();
    });

    App.start();

});