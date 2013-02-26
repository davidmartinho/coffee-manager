define([
    'backbone',
    'models/Order'
], function(Backbone, OrderModel) {

    return Backbone.Collection.extend({

        url: "api/orders",
        model: OrderModel,

        parse: function(response){
            return response.orders;
        }

    });
});