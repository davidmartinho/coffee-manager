define([
    'jquery',
    'underscore',
    'mustache',
    'backbone',
    'marionette',
    'router',
    'coffee',
    'text!templates/BatchList.html',
    'i18n!nls/BatchList'
], function($, _, Mustache, Backbone, Marionette, Router, Coffee, tpl, i18n) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        onDomRefresh: function() {
            Coffee.parseTimestamps();
        },

        serializeData: function() {
            return _.extend({}, this.model, i18n);
        }
    });
});