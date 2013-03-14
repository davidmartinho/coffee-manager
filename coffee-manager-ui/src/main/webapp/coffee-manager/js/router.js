define([
    'jquery',
    'underscore',
    'mustache',
    'backbone',
    'marionette',
    'coffee',
    'app',
    'appLayout'
], function($, _, Mustache, Backbone, Marionette, Coffee, App, AppLayout) {

    var Router = Backbone.Marionette.AppRouter.extend({

        initialize: function() {
            App.layout = new AppLayout();
            App.layout.render();
            App.page.show(App.layout);
        },

        appRoutes: {
            "" : "listOrders",
            "orders" : "listOrders",
            "orders/:id" : "showOrder",
            "orders/:id/edit" : "editOrder",
            "order/create" : "createOrder",
            "items" : "listItems",
            "items/create" : "createItem",
            "batches" : "listBatches",
            "batches/:id" : "showBatch",
			"not-found" : "showNotFound",
			"forbidden" : "showForbidden"
        },

        controller: {

            listOrders: function() {
                var that = this;
                var orderCollection = Coffee.OrderCollection;
                orderCollection.fetch({
                    success : function() {
                        require(['views/OrderList', 'app'], function(OrderListView, App) {
                            App.layout.contentRegion.show(
                                new OrderListView({ collection : orderCollection }));
                        });
                        that.selectMenuItem("order-list-menu");
                    }
                });
            },

            showOrder: function(id) {
                var that = this;
                var orderCollection = Coffee.OrderCollection;
                orderCollection.fetch({ success: function() {
                    require(['views/Order'], function(OrderView) {
                        App.layout.contentRegion.show(
                            new OrderView({ model : orderCollection.get(id) }));
                    });
                }});
            },

            editOrder: function(id) {
                var that = this;
                var itemCollection = Coffee.ItemCollection;
                itemCollection.fetch({ success: function() {
                    require(['views/OrderEdit'], function(OrderEditView) {
                        App.layout.contentRegion.show(
                            new OrderEditView({ collection : itemCollection, id: id }));
                    });
                }});
            },

            createOrder : function() {
                var that = this;
                var itemCollection = Coffee.ItemCollection;
                itemCollection.fetch({
                    success : function() {
                        require(['views/OrderCreate'], function(OrderCreateView) {
                            App.layout.contentRegion.show(new OrderCreateView({ collection: itemCollection }));
                            that.selectMenuItem('order-create-menu');
                        });
                    }
                });
            },

            listItems : function() {
                var that = this;
                var itemCollection = Coffee.ItemCollection;
                itemCollection.fetch({
                    success : function() {
                        require(['views/ItemList'], function(ItemListView) {
                            App.layout.contentRegion.show(
                                new ItemListView({ collection : itemCollection }));
                        });
                        that.selectMenuItem("item-list-menu");
                    }
                });
            },

            createItem : function() {
                var that = this;
                require(['views/ItemCreate'], function(ItemCreateView) {
                    App.layout.contentRegion.show(new ItemCreateView());
                    that.selectMenuItem('item-create-menu');
                });
            },

            listBatches: function() {
                var that = this;
                var batchCollection = Coffee.BatchCollection;
                batchCollection.fetch({
                    success : function() {
                        require(['views/BatchList'], function(BatchListView) {
                            App.layout.contentRegion.show(
                                new BatchListView({ collection : batchCollection }));
                        });
                        that.selectMenuItem("batch-list-menu");
                    }
                });
            },

            showBatch: function(id) {
                var that = this;
                if(id === "sent" || id === "not-sent" || id === "received") {
                    this.listBatches();
                } else {
                    var batchCollection = Coffee.BatchCollection;
                    batchCollection.fetch({ success: function() {
                        require(['views/Batch'], function(BatchView) {
                            App.layout.contentRegion.show(
                                new BatchView({ model : batchCollection.get(id) }));
                        });
                    }});
                }
            },

            showNotFound: function() {
				var that = this;
				require(['bennuViews/NotFound'], function(NotFoundView) {
					App.page.show(new NotFoundView());					
				});
			},

            showForbidden: function() {
				var that = this;
				require(['bennuViews/Forbidden'], function(ForbiddenView) {
					App.page.show(new ForbiddenView());					
				});
			},

            selectMenuItem : function(menuItem) {
                $('.nav li').removeClass('active');
                if (menuItem) {
                    $('.' + menuItem).addClass('active');
                }
            }
        }

    });

    var initialize = function() {
        if(App.router === undefined) {
            App.router = new Router();
        }
    }

    return {
        initialize: initialize
    }

});