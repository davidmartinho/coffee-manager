define([
    'backbone',
    'moment',
    'router',
    'coffee',
    'text!templates/Batch.html'
], function(Backbone, Moment, Router, Coffee, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        onDomRefresh: function() {
            Coffee.parseTimestamps();
        },

        serializeData: function() {
            return {
                "batch": this.model.toJSON()
            }
        }
    });
});