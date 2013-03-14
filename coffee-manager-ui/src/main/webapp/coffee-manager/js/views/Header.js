define([
    'jquery',
    'underscore',
    'marionette',
    'text!templates/Header.html',
    'i18n!nls/Header'
], function($, _, Marionette, tpl, i18n) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        onShow: function() {
            $('.dropdown-toggle').dropdown();
        },

        serializeData: function() {
            return _.extend({}, this.model, i18n);
        },

        selectMenuItem : function(menuItem) {
            $('.nav li').removeClass('active');
            if (menuItem) {
                $('.' + menuItem).addClass('active');
            }
        }
    });
});