define([
    'backbone',
    'models/Batch'
], function(Backbone, BatchModel) {

    return Backbone.Collection.extend({

        url: "/api/coffee-manager/batches",
        model: BatchModel,

        parse: function(response){
            return response.batches;
        }

    });
});