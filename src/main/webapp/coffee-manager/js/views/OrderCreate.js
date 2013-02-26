define([
    'jquery',
    'backbone',
    'marionette',
    'coffee',
    'app',
    'text!templates/OrderCreate.html'
], function($, Backbone, Marionette, Coffee, App, tpl) {

    return Backbone.Marionette.ItemView.extend({

        template: tpl,

        events: {
            "click #create-order" : "createOrder"
        },

        createOrder: function(e) {
            e.preventDefault();
            order = new Array();
            $('input[name^=quantity]', this.el).filter(function(index, element) {
                return $(element).val();
            }).each(function(index, element) {
                    var id = $(element).attr("id");
                    var quantity = parseInt($(element).val());
                    order.push({
                        id : id,
                        quantity : quantity
                    });
            });
            console.log(order);
            require(['models/Order'], function(OrderModel) {
                var newOrderModel = new OrderModel();
                newOrderModel.save({ entries: order }, {
                        success: function(model) {
                            App.router.navigate("/orders/"+model.get("id"), {trigger: true});
                        }
                    }
                );
            });
        },

        onDomRefresh: function() {
            Coffee.parseTimestamps();
        }

    });
});