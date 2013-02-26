define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'router',
    'coffee',
    'text!templates/Order.html'
], function($, Mustache, Backbone, Moment, Router, Coffee, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        onDomRefresh: function() {
            Coffee.parseTimestamps();
        }
    });
});