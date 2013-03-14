define([
    'backbone',
    'marionette',
    'coffee',
    'app',
    'text!templates/AppLayout.html'
], function(Backbone, Marionette, Coffee, App, tpl) {

    return Backbone.Marionette.Layout.extend({

        template: tpl,

        regions: {
            headerRegion: "#header",
            contentRegion: "#content",
            footerRegion: "#footer"
        },

        onShow: function() {
            Coffee.ProfileModel.fetch({
                success : function() {
                    require(['views/Header'], function(HeaderView) {
                        var headerView = new HeaderView({ model: Coffee.ProfileModel.toJSON() });
                        App.layout.headerRegion.show(headerView);
                    });
                    require(['views/Footer'], function(FooterView) {
                        App.layout.footerRegion.show(new FooterView());
                    });
                }
            });
        }
    });
});