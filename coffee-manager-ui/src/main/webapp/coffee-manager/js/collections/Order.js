define([
    'backbone',
    'models/Order'
], function(Backbone, OrderModel) {

    return Backbone.Collection.extend({

        url: "/api/coffee-manager/orders",
        model: OrderModel,

        parse: function(response){
            return response.orders;
        }

    });
});