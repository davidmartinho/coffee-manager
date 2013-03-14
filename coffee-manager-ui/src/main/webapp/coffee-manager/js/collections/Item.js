define([
    'backbone',
    'models/Item'
], function(Backbone, ItemModel) {

    return Backbone.Collection.extend({

        url: "/api/coffee-manager/items",
        model: ItemModel,

        parse: function(response){
            return response.items;
        }

    });
});