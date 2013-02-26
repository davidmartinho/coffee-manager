define([
    'backbone',
    'models/Batch'
], function(Backbone, BatchModel) {

    return Backbone.Collection.extend({

        url: "api/batches",
        model: BatchModel,

        parse: function(response){
            return response.batches;
        }

    });
});