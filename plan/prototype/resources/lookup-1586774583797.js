(function(window, undefined) {
  var dictionary = {
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "my_recipes",
    "b39a0eaa-db4b-41e6-a93c-d93572a6c88a": "collection",
    "33a36ffa-b361-4332-96ad-a55872a2c66a": "my_collections_context_menu",
    "1339c30f-fae4-4ce0-b53d-8ccddedca792": "add_to_collection_new_collection",
    "94a9004e-197b-4936-b208-7151a1b4a8f7": "recipe",
    "782b1589-42db-46db-8f44-6cbbe66f8e37": "recipe_edit",
    "8fc29134-8534-43bd-b742-db4422abf8d3": "my_collections",
    "02af47ae-eb21-4391-94ad-b8d062849776": "collection_edit",
    "c2f989cf-f869-4e90-a5be-0bde76df88d4": "add_to_collection",
    "b355d67c-9603-4b2d-a854-052d4d4d4d6b": "my_recipes_context_menu",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "bb8abf58-f55e-472d-af05-a7d1bb0cc014": "default"
  };

  var uriRE = /^(\/#)?(screens|templates|masters|scenarios)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);