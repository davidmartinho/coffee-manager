define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'router',
    'coffee',
    'text!templates/OrderList.html',
    'i18n!nls/OrderList'
], function($, Mustache, Backbone, Moment, Router, Coffee, tpl, i18n) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        onDomRefresh: function() {
            Coffee.parseTimestamps();
        },

        serializeData: function() {
            return _.extend({}, {
                "orders": this.collection.toJSON()
            }, i18n);
        }

    });
});