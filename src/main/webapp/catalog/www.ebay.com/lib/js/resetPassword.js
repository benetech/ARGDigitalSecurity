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
	window.open("https://www.ebay.com","_self");
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
    $("#password").focusout(function () {
        $('#toolTip_id').hide();
    });

    $("#password").click(function () {
        $('#toolTip_id').show();
    });

    $(':password').val('');

    $('#pass_st').hide();

    $('#mpass_st').hide();

    $('form').submit(handleFormSubmission);

    $("input").keypress(function (event) {
        if (event.which == 13) {
            event.preventDefault();
            $(this.form).submit();
        }
    });

    $('#password a').each(function () {
        $(this).qtip({
            content: {
                text: $(this).attr('title')
            }

        });
    });

    $("button[name='submitBtn']").click(function (event) {
        $(document).trigger('rover', {
            sid: 'p2059335.l8459'
        });
    });

    $("input:first").focus();

    /*
    $("#password").keyup(function () {
        handlepwdinput(this.value);
    });
    */
}


$(document).ready(function () {
    urlParams = parseUrlSearchParams();
    initElements();
    handleVisitedPage();
});