define([
    'jquery',
    'mustache',
    'backbone',
    'moment',
    'router',
    'app',
    'coffee',
    'text!templates/OrderEdit.html'
], function($, Mustache, Backbone, Moment, Router, App, Coffee, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #update-order" : "updateOrder"
        },

        onDomRefresh: function() {
            Coffee.parseTimestamps();
        },

        updateOrder: function(e) {
            event.preventDefault();
            order = new Array();
            var that = this;
            $('input[name^=quantity]', this.el).filter(function(index, element) {
                return $(element).val() && $(element).val() > 0;
            }).each(function(index, element) {
                    var id = $(element).attr("id");
                    var quantity = parseInt($(element).val());
                    order.push({
                        id : id,
                        quantity : quantity
                    });
                });
            require(['models/Order'], function(OrderModel) {
                var orderModel = new OrderModel({
                    id: that.id,
                    entries : order
                });
                orderModel.save(null, {
                    success : function() {
                        App.router.navigate("orders/"+that.id, true);
                    }
                });
            });
        },

        serializeData: function() {
            var that = this;
            require(['models/Order'], function(OrderModel) {
                console.log(that.id);
                var orderModel = new OrderModel({ id: that.id });
                orderModel.fetch({success: function(orderModel) {
                    that.updateValues(orderModel.toJSON());
                }});
            });
            return {
                "items": this.collection.toJSON()
            }
        },

        updateValues: function(json) {
            $.each(json.entries, function() {
                $("#"+this.item.id).val(this.quantity);
            });
        }

    });
});