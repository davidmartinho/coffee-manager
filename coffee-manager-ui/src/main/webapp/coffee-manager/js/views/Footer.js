define([
    'jquery',
    'mustache',
    'backbone',
    'router',
    'coffee',
    'text!templates/Footer.html'
], function($, Mustache, Backbone, Router, Coffee, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl

    });
});