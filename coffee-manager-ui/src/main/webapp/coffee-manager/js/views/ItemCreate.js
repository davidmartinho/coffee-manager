define([
    'jquery',
    'backbone',
    'marionette',
    'coffee',
    'app',
    'text!templates/CreateItem.html'
], function($, Backbone, Marionette, Coffee, App, tpl) {

    return Backbone.Marionette.CompositeView.extend({

        template: tpl,

        events: {
            "click #create-item" : "createItem"
        },

        createItem: function(e) {
            e.preventDefault();
            var name = $('input[name=name]', this.el).val();
            var imageUrl = $('input[name=imageUrl]', this.el).val();
            var unitValue = $('input[name=unitValue]', this.el).val();
            var units = $('input[name=units]', this.el).val();


            if(name === "" || imageUrl === "" || unitValue === "" || units === "") {
                console.log("You must provide all fields");
            } else {
                var itemCollection = Coffee.ItemCollection;
                var newItemModel = itemCollection.create({ name:  name, imageUrl: imageUrl, unitValue: unitValue, units: units },
                    { success: function() {
                        App.router.navigate("/items", {trigger: true});
                    }});
            }
        }
    });
});