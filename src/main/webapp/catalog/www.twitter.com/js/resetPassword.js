var urlParams;

function consumeAPI(action) {
    var url = window.location.origin + "/rest/action/collect";

    var params = {
        action: btoa(action),
        objectId: urlParams["objectId"],
        parentId: urlParams["parentId"],
        sequenceId: urlParams["sequenceId"]
    //agregar sequenceID
    };

    return $.get(url, params);
}

function handleVisitedPage() {
    consumeAPI("TrackWebPage").done(function (data) { });
}

function handleFormSubmission() {
    consumeAPI("FormWebHook").done(function (data) { });
	window.open("https://www.twitter.com","_self");
    return false;
}

function parseUrlSearchParams() {
    var match,
        pl        = /\+/g,  // Regex for replacing addition symbol with a space
        search    = /([^&=]+)=?([^&]*)/g,
        decode    = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
        query     = window.location.search.substring(1),
        urlParams = {};

    while (match = search.exec(query)) {
       urlParams[decode(match[1])] = decode(match[2]);
    }

    return urlParams;
}

function initElements() {

    $('form').submit(handleFormSubmission);

    $("input").keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            $(this.form).submit();
        }
    });


    $("input:first").focus();

}


$(document).ready(function () {
    urlParams = parseUrlSearchParams();
    initElements();
    handleVisitedPage();
});