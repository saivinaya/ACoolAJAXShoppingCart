// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */
function addToCart(itemCode) {
//creating a new XMLHttp Request
    var req = newXMLHttpRequest();
// Assigning statechange Handler
    req.onreadystatechange = getReadyStateHandler(req, updateCart);

    req.open("POST", "cart.do", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    //sending the request to the server
    req.send("action=add&item=" + itemCode);
}


/*
 * Update shopping-cart area of page to reflect contents of cart
 * described in XML document.
 */
function updateCart(cartXML) {
    //reading the  cart value using cart tag
    var cart = cartXML.getElementsByTagName("cart")[0];
    // getting the timestamp fromm generate tag in the cart
    var generated = cart.getAttribute("generated");
    //updating the display only if the timestamp is new
    if (generated > lastCartUpdate) {
        // assigning new timestamp to lastCartUpdate 
        lastCartUpdate = generated;
        //getting the part of the page having ID as "contents" which was defined in index.jsp previously
        var contents = document.getElementById("contents");
        // making it null, clearing the part of the page
        contents.innerHTML = "";

        // iterating through the items in the cart one by one and adding them to the web page
        var items = cart.getElementsByTagName("item");
        for (var I = 0; I < items.length; I++) {

            var item = items[I];
            var name = item.getElementsByTagName("name")[0].firstChild.nodeValue;
            var quantity = item.getElementsByTagName("quantity")[0].firstChild.nodeValue;

            var listItem = document.createElement("li");
            // adding the items and quatity to the web page
            listItem.appendChild(document.createTextNode(name + " x " + quantity));
            contents.appendChild(listItem);
        }

    }
// adding the total value to the part of the web page having ID as "total"
    document.getElementById("total").innerHTML = cart.getAttribute("total");
}