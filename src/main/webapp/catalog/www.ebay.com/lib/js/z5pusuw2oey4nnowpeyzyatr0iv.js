(function() {
    for (var b = "abbr article aside audio canvas datalist details figcaption figure footer header hgroup mark meter nav output progress section summary time video".split(" "), a = 0, c = b.length; a < c; a++) document.createElement(b[a])
})();
window.respond = function(d, e, c) {
    function f() {
        b.documentWidth = k()
    }

    function g() {
        m(!0)
    }
    for (var b = {}, r = e.documentElement, n = [], o = [], j = [], p = {}, l = e.getElementsByTagName("head")[0] || r, s = l.getElementsByTagName("link"), i = function() {
                for (var a = e.styleSheets, b = a.length, h = 0; h < b; h++) {
                    var c = a[h].href;
                    c && !p[c] && A(c, function(a) {
                        for (var b = c, h = (a = a.match(/@media ([^\{]+)\{([\S\s]+?)(?=\}\/\*\/mediaquery\*\/)/gmi)) && a.length || 0, b = b.substring(0, b.lastIndexOf("/")) + "/", e = 0; e < h; e++) {
                            var d = (a[e].match(/@media ([^\{]+)\{([\S\s]+?)$/) &&
                                    RegExp.$1).split(","),
                                z = d.length;
                            o.push(RegExp.$2 && RegExp.$2.replace(/(url\()['"]?([^\/\)'"][^:\)'"]+)['"]?(\))/g, "$1" + b + "$2$3"));
                            for (var f = 0; f < z; f++) {
                                var g = d[f];
                                n.push({
                                    media: g.match(/(only\s+)?([a-zA-Z]+)(\sand)?/) && RegExp.$2,
                                    rules: o.length - 1,
                                    minw: g.match(/\(min\-width:\s?(\s?[0-9]+)px\s?\)/) && parseFloat(RegExp.$1),
                                    maxw: g.match(/\(max\-width:\s?(\s?[0-9]+)px\s?\)/) && parseFloat(RegExp.$1)
                                })
                            }
                        }
                        m();
                        p[c] = !0
                    })
                }
            }, k = function() {
                var a = r.clientWidth;
                return docWidth = "CSS1Compat" === e.compatMode && a || e.body.clientWidth ||
                    a
            }, q, t, u = function() {
                for (var a in j) j[a] && j[a].parentNode === l && l.removeChild(j[a])
            }, m = function(a) {
                var c = !b.forced ? k() : b.forcedWidth,
                    h = {},
                    d = e.createDocumentFragment(),
                    f = s[s.length - 1],
                    g = (new Date).getTime();
                if (a && q && 30 > g - q) clearTimeout(t), t = setTimeout(m, 30);
                else {
                    q = g;
                    for (var i in n)
                        if (a = n[i], !a.minw && !a.maxq || (!a.minw || a.minw && c >= a.minw) && (!a.maxw || a.maxw && c <= a.maxw)) h[a.media] || (h[a.media] = []), h[a.media].push(o[a.rules]);
                    u();
                    for (i in h) c = e.createElement("style"), a = h[i].join("\n"), c.type = "text/css", c.media =
                        i, c.styleSheet ? c.styleSheet.cssText = a : c.appendChild(e.createTextNode(a)), d.appendChild(c), j.push(c);
                    l.insertBefore(d, f.nextSibling)
                }
            }, A = function(a, c) {
                var b = v();
                b && (b.open("GET", a, !0), b.onreadystatechange = function() {
                    4 != b.readyState || 200 != b.status && 304 != b.status || c(b.responseText)
                }, 4 != b.readyState && b.send())
            }, v, w = !1, x = [function() {
                return new ActiveXObject("Microsoft.XMLHTTP")
            }, function() {
                return new ActiveXObject("Msxml3.XMLHTTP")
            }, function() {
                return new ActiveXObject("Msxml2.XMLHTTP")
            }, function() {
                return new XMLHttpRequest
            }],
            y = x.length; y--;) {
        try {
            w = x[y]()
        } catch (B) {
            continue
        }
        break
    }
    v = function() {
        return w
    };
    b.mediaQueriesSupported = c;
    b.update = i;
    b.force = function(a) {
        b.forced = !a || void 0 === typeof a ? !1 : !0;
        b.forcedWidth = !a || void 0 === typeof a ? !1 : a;
        b.documentWidth = !a || void 0 === typeof a ? k() : !1;
        !a || void 0 === typeof a ? b.mediaQueriesSupported ? (u(), p = {}) : i() : i()
    };
    b.forced = !1;
    b.forcedWidth = !1;
    b.documentWidth = k();
    d.addEventListener ? d.addEventListener("resize", f, !1) : d.attachEvent && d.attachEvent("onresize", f);
    if (c && !b.forced) return b;
    i();
    d.addEventListener ?
        d.addEventListener("resize", g, !1) : d.attachEvent && d.attachEvent("onresize", g);
    return b
}(this, this.document, function(d, e) {
    var c;
    c = 3;
    for (var f = document.createElement("div"), g = f.getElementsByTagName("i"); f.innerHTML = "<\!--[if gt IE " + ++c + "]><i></i><![endif]--\>", g[0];);
    c = 4 < c ? c : void 0;
    if (d.matchMedia || c && 9 <= c) return !0;
    if (c && 8 >= c) return !1;
    c = e.documentElement;
    var f = e.createElement("body"),
        g = e.createElement("div"),
        b = e.createElement("style");
    g.setAttribute("id", "qtest");
    b.type = "text/css";
    f.appendChild(g);
    b.styleSheet ? b.styleSheet.cssText = "@media only all { #qtest { position: absolute; } }" : b.appendChild(e.createTextNode("@media only all { #qtest { position: absolute; } }"));
    c.insertBefore(f, c.firstChild);
    c.insertBefore(b, f);
    support = "absolute" == (d.getComputedStyle ? d.getComputedStyle(g, null) : g.currentStyle).position;
    c.removeChild(f);
    c.removeChild(b);
    return support
}(this, this.document));
raptor.defineClass("raptor.tracking.core.Tracker", function(e) {
    var j = e.require("ebay.cookies"),
        m = function(a) {
            var b = $trk = this;
            e.extend(b, b.config = a);
            b.image = $("<img/>").css("display", "none");
            b.rover.sync && b.image.attr("src", b.rover.sync);
            e.bind(b, document, "click", b.onBody);
            e.bind(b, document, "mousedown", b.onMouse);
            e.bind(b, document, "rover", b.onRover);
            e.bind(b, document, "track", b.onTrack);
            b.originalPSI = b.currentPSI = a.psi;
            $("body").bind("TRACKING_UPDATE_PSI", function(a, c) {
                c && c.psi && (b.currentPSI = c.psi);
                c && (c.callback && "function" == typeof c.callback) && c.callback.call(b)
            });
            $("body").bind("ADD_LAYER_PSI", function(a, c) {
                c && (b.currentPSI += c)
            });
            $("body").bind("CLEAR_LAYER_PSI", function() {
                b.currentPSI = b.originalPSI
            })
        };
    e.extend(m.prototype, {
        codes: "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxy-_!".split(""),
        sizes: {
            p: 4,
            c: 1,
            m: 3,
            l: 3
        },
        start: {
            p: 1,
            c: 5,
            m: 6,
            l: 9
        },
        target: function(a) {
            return a.tagName ? a : a.target
        },
        attrib: function(a, b) {
            return a.getAttribute ? a.getAttribute(b) : null
        },
        valid: function(a, b) {
            return b &&
                0 < b.indexOf(".ebay.") ? a : null
        },
        trackable: function(a) {
            a = a.tagName;
            return a.match(/^a$|area/i) ? this.valid(a, a.href) : a.match(/input/) && a.type.match(/submit/i) && a.form ? this.valid(a, form.action) : null
        },
        click: function(a) {
            for (var a = this.target(a), b = null; a && a.tagName; a = a.parentNode)
                if (b = b || this.trackable(a), this.attrib(a, "_sp")) return this.clickElement(a, b);
            this.pid && this.track(this.pid)
        },
        clickElement: function(a, b) {
            var d = this.attrib(a, "_sp");
            this.track(d.split(";")[0], b ? this.attrib(b, "_l") : null)
        },
        track: function(a,
            b) {
            var d = a.split(".");
            a.match(/p\d+/) || d.push(this.pid);
            b && d.push(b);
            for (var c = j.readCookie("ds2", "sotr"), k = this.chars(c && "b" == c.charAt(0) ? c : "bzzzzzzzzzzz"), f = 0, l = d.length; f < l; f++) {
                var g = d[f].match(/([pcml])(\d+)/);
                if (null != g)
                    for (var h = g[1], c = this.sizes[h], h = this.start[h], g = this.chars(this.encode(g[2], c)), i = 0; i < c; i++) k[h + i] = g[i]
            }
            f = 0;
            l = k.length;
            for (c = ""; f < l;) c = c.concat(k[f++]);
            j.writeCookielet("ds2", "sotr", c);
            e.log("debug", "track", d.join("."), c)
        },
        chars: function(a) {
            for (var b = 0, d = a.length, c = []; b < d;) c.push(a.charAt(b++));
            return c
        },
        encode: function(a, b) {
            for (var d = this.codes, c = ""; 64 <= a; a = a / 64 | 0) c = d[a % 64] + c;
            c = (0 <= a ? d[a] : "") + c;
            return c.concat("zzzz").substring(0, b)
        },
        onBody: function(a) {
            this.click(a)
        },
        onMouse: function() {
            j.writeCookielet("ebay", "psi", this.currentPSI);
            e.log("debug", "psi", this.currentPSI)
        },
        onTrack: function(a, b) {
            var d = b.trksid;
            d && this.track(d, b.trklid)
        },
        onRover: function(a, b) {
            var d = b.imp,
                c = $uri(this.rover.uri + (d ? this.rover.imp : this.rover.clk));
            d && (c.params.imp = d);
            delete b.imp;
            c.params.trknvp = c.encodeParams(b);
            c.params.ts = (new Date).valueOf().toString();
            this.image.attr("src", c.getUrl(), c.params);
            e.log("debug", c.getUrl())
        }
    });
    return m
});
raptor.defineClass("raptor.tracking.idmap.IdMap", function(b) {
    var d = b.require("ebay.cookies"),
        e = function() {};
    b.extend(e, {
        roverService: function(a) {
            this.url = $uri(a || "");
            !this.url.protocol.match(/https/) && !d.readCookie("dp1", "idm") && b.bind(this, window, "load", this.sendRequest)
        },
        sendRequest: function() {
            this.url.appendParam("cb", "raptor.require('raptor.tracking.idmap.IdMap').handleResponse");
            $.ajax({
                url: this.url.getUrl(),
                dataType: "jsonp",
                jsonp: !1
            })
        },
        handleResponse: function(a) {
            this.image = $("<img/>").css("display",
                "none");
            for (var c = 0, b = a.length - 1; c < b; c++) a[c] && this.image.attr("src", a[c]);
            b && this.setCookieExpiration(a[b])
        },
        setCookieExpiration: function(a) {
            "number" == typeof a && 0 < a && d.writeCookielet("dp1", "idm", "1", a / 86400, "")
        }
    });
    return e
});
raptor.require("raptor.tracking.idmap.IdMap");