define([
    'backbone',
    'models/User'
], function(Backbone, UserModel) {

    return Backbone.Collection.extend({

        url: "api/users",
        model: UserModel,

        parse: function(response){
            return response.users;
        }

    });
});