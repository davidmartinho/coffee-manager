define([
    'collections/Batch',
    'collections/Order',
    'collections/Item',
    'models/Order',
    'models/Profile',
    'models/Login',
    'models/User'
], function(BatchCollection, OrderCollection, ItemCollection, OrderModel, ProfileModel, LoginModel, UserModel) {

    var Coffee = Coffee || {};

    /** THE PLACE TO STORE THE COLLECTIONS AND MODELS **/
    Coffee.BatchCollection = Coffee.BatchCollection || new BatchCollection();
    Coffee.OrderCollection = Coffee.OrderCollection || new OrderCollection();
    Coffee.ItemCollection = Coffee.ItemCollection || new ItemCollection();

    Coffee.ProfileModel = Coffee.ProfileModel || new ProfileModel();
    Coffee.LoginModel = Coffee.LoginModel || new LoginModel();
    Coffee.UserModel = Coffee.UserModel || new UserModel();
    Coffee.OrderModel = Coffee.OrderModel || new OrderModel();

    Coffee.parseTimestamps = function() {
        $('.timestamp').each(function() {
            $(this).text(moment($(this).text()).fromNow());
        });
        $('.interval').each(function() {
            var text = $(this).text();
            var tokens = text.split(",");
            var start = moment(tokens[0]);
            var end = moment(tokens[1]);
            $(this).text(start.from(end, true));
        });
    }

    Coffee.showError = function(msg) {
        alert(msg);
    }

    return Coffee;

});

