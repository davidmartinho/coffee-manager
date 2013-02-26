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
            "users" : "listUsers",
            "items" : "listItems",
            "items/create" : "createItem",
            "batches" : "listBatches",
            "batches/:id" : "showBatch",
            "login" : "login",
            "logout" : "logout"
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

            listUsers : function() {
                var that = this;
                var userCollection = Coffee.UserCollection;
                userCollection.fetch({
                    success : function() {
                        require(['views/UserList'], function(UserListView) {
                            App.layout.contentRegion.show(
                                new UserListView({ collection : userCollection }));
                        });
                        that.selectMenuItem("user-list-menu");
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

            login : function() {
                if (localStorage["auth"]) {
                    localStorage.removeItem("auth");
                }
                require(['views/Login'], function(LoginView) {
                    App.page.show(new LoginView());
                });
            },

            logout : function() {
                localStorage.removeItem("auth");
                require(['views/Login'], function(LoginView) {
                    App.page.show(new LoginView());
                    App.router.navigate("login", { trigger: true });
                });
            },

            selectMenuItem : function(menuItem) {
                $('.nav li').removeClass('active');
                if (menuItem) {
                    $('.' + menuItem).addClass('active');
                }
            },

            login : function() {
                require(['bennuViews/Login'], function(LoginView) {
                    App.page.show(new LoginView());
                });
            },

            logout : function() {
                require(['views/Login'], function(LoginView) {
                    App.page.show(new LoginView());
                    App.router.navigate("login", { trigger: true });
                });
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