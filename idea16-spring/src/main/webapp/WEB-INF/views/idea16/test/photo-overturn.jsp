<%--
  Created by IntelliJ IDEA.
  User: Zora
  Date: 2017/2/23
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <style>
        body {
            background-color:#EFEFEF
        }
        div.flip-3d {
            perspective:1200px;
            width:30%;
            float:left;
        }
        div.flip-3d figure {
            position:relative;
            transform-style:preserve-3d;
            transition:1s transform;
            font-size:1.6rem;
        }
        div.flip-3d
        figure img {
            width:100%;
            height:auto;
        }
        div.flip-3d figure figcaption {
            position:absolute;
            width:100%;
            height:100%;
            top:0;
            transform:rotateY(.5turn)
            translateZ(1px);
            background:rgba(255,255,255,0.9);
            text-align:center;
            padding-top:45%;
            opacity:0.6;
            transition:1s .5s opacity;
        }
        div.flip-3d:hover
        figure {
            transform:rotateY(.5turn);
        }
        div.flip-3d:hover figure figcaption {
            opacity:1;
        }
        div.flip-3d figure:after {
            content:" ";
            display:block;
            height:8vw;
            width:100%;
            transform:rotateX(90deg);
            background-image:radial-gradient(ellipse closest-side,rgba(0,0,0,0.2) 0%,rgba(0,0,0,0) 100%);
        }
        @media screen and (max-width:800px) {
            div#flip-3d {
                perspective-origin:center top;
            }
            div#flip-3d figure {
                float:none;
                width:50%;
                margin:0 auto;
                margin-bottom:12vw;
            }
            div.flip-3d figure figcaption {
                font-size:0.8rem;
            }
            div#flip-3d figure:last-child {
                display:none;
            }
        }</style>
<div class="jq22-container">
    <div class="jq22-content">
        <div class="flip-3d">
            <figure> <img src="//www.jq22.com/img/cs/500x500.png" alt="">
                <figcaption> Mouse </figcaption>
            </figure>
        </div>
        <div class="flip-3d">
            <figure> <img src="//www.jq22.com/img/cs/500x500.png" alt="">
                <figcaption> Irrigation rats </figcaption>
            </figure>
        </div>
        <div class="flip-3d">
            <figure> <img src="//www.jq22.com/img/cs/500x500.png" alt="">
                <figcaption> Rabbit </figcaption>
            </figure>
        </div>
    </div>
</div>
<script>
    // StyleFix 1.0.1 & PrefixFree 1.0.4 / by Lea Verou / MIT license
    (function() {
        function f(a, b) {
            return [].slice.call((b || document).querySelectorAll(a))
        }
        if (window.addEventListener) {
            var b = window.StyleFix = {
                link: function(a) {
                    try {
                        if (!/\bstylesheet\b/i.test(a.rel) || !a.sheet.cssRules) return
                    } catch(c) {
                        return
                    }
                    var d = a.href || a.getAttribute("data-href"),
                        g = d.replace(/[^\/]+$/, ""),
                        e = a.parentNode,
                        h = new XMLHttpRequest;
                    h.open("GET", d);
                    h.onreadystatechange = function() {
                        if (h.readyState === 4) {
                            var c = h.responseText;
                            if (c && a.parentNode) {
                                c = b.fix(c, true, a);
                                g && (c = c.replace(/url\((?:'|")?(.+?)(?:'|")?\)/gi,
                                    function(a, c) {
                                        return ! /^([a-z]{3,10}:|\/)/i.test(c) ? 'url("' + g + c + '")': a
                                    }), c = c.replace(RegExp("\\b(behavior:\\s*?url\\('?\"?)" + g, "gi"), "$1"));
                                var d = document.createElement("style");
                                d.textContent = c;
                                d.media = a.media;
                                d.disabled = a.disabled;
                                d.setAttribute("data-href", a.getAttribute("href"));
                                e.insertBefore(d, a);
                                e.removeChild(a)
                            }
                        }
                    };
                    h.send(null);
                    a.setAttribute("data-inprogress", "")
                },
                styleElement: function(a) {
                    var c = a.disabled;
                    a.textContent = b.fix(a.textContent, true, a);
                    a.disabled = c
                },
                styleAttribute: function(a) {
                    var c = a.getAttribute("style"),
                        c = b.fix(c, false, a);
                    a.setAttribute("style", c)
                },
                process: function() {
                    f('link[rel~="stylesheet"]:not([data-inprogress])').forEach(StyleFix.link);
                    f("style").forEach(StyleFix.styleElement);
                    f("[style]").forEach(StyleFix.styleAttribute)
                },
                register: function(a, c) { (b.fixers = b.fixers || []).splice(c === void 0 ? b.fixers.length: c, 0, a)
                },
                fix: function(a, c) {
                    for (var d = 0; d < b.fixers.length; d++) a = b.fixers[d](a, c) || a;
                    return a
                },
                camelCase: function(a) {
                    return a.replace(/-([a-z])/g,
                        function(a, b) {
                            return b.toUpperCase()
                        }).replace("-", "")
                },
                deCamelCase: function(a) {
                    return a.replace(/[A-Z]/g,
                        function(a) {
                            return "-" + a.toLowerCase()
                        })
                }
            }; (function() {
                setTimeout(function() {
                        f('link[rel~="stylesheet"]').forEach(StyleFix.link)
                    },
                    10);
                document.addEventListener("DOMContentLoaded", StyleFix.process, false)
            })()
        }
    })();
    (function(f) {
        if (window.StyleFix && window.getComputedStyle) {
            var b = window.PrefixFree = {
                prefixCSS: function(a, c) {
                    function d(c, d, g, e) {
                        c = b[c];
                        c.length && (c = RegExp(d + "(" + c.join("|") + ")" + g, "gi"), a = a.replace(c, e))
                    }
                    var g = b.prefix;
                    d("functions", "(\\s|:)", "\\s*\\(", "$1" + g + "$2(");
                    d("keywords", "(\\s|:)", "(\\s|;|\\}||$)", "$1" + g + "$2$3");
                    d("properties", "(^|\\{|\\s|;)", "\\s*:", "$1" + g + "$2:");
                    if (b.properties.length) {
                        var e = RegExp("\\b(" + b.properties.join("|") + ")(?!:)", "gi");
                        d("valueProperties", "\\b", ":(.+?);",
                            function(a) {
                                return a.replace(e, g + "$1")
                            })
                    }
                    c && (d("selectors", "", "\\b", b.prefixSelector), d("atrules", "@", "\\b", "@" + g + "$1"));
                    return a = a.replace(RegExp("-" + g, "g"), "-")
                },
                prefixSelector: function(a) {
                    return a.replace(/^:{1,2}/,
                        function(a) {
                            return a + b.prefix
                        })
                },
                prefixProperty: function(a, c) {
                    var d = b.prefix + a;
                    return c ? StyleFix.camelCase(d) : d
                }
            }; (function() {
                var a = {},
                    c = "",
                    d = 0,
                    g = [],
                    e = getComputedStyle(document.documentElement, null),
                    h = document.createElement("div").style,
                    j = function(b) {
                        g.indexOf(b) === -1 && g.push(b);
                        if (b.indexOf("-") > -1) {
                            var e = b.split("-");
                            if (b.charAt(0) === "-") {
                                var b = e[1],
                                    f = ++a[b] || 1;
                                a[b] = f;
                                for (d < f && (c = b, d = f); e.length > 3;) e.pop(),
                                    f = e.join("-"),
                                StyleFix.camelCase(f) in h && (b = g, b.indexOf(f) === -1 && b.push(f))
                            }
                        }
                    };
                if (e.length > 0) for (var i = 0; i < e.length; i++) j(e[i]);
                else for (var f in e) j(StyleFix.deCamelCase(f));
                b.prefix = "-" + c + "-";
                b.Prefix = StyleFix.camelCase(b.prefix);
                g.sort();
                b.properties = [];
                for (i = 0; i < g.length; i++) {
                    f = g[i];
                    if (f.charAt(0) !== "-") break;
                    f.indexOf(b.prefix) === 0 && (e = f.slice(b.prefix.length), StyleFix.camelCase(e) in h || b.properties.push(e))
                }
                b.Prefix == "Ms" && !("transform" in h) && !("MsTransform" in h) && "msTransform" in h && b.properties.push("transform", "transform-origin");
                b.properties.sort()
            })(); (function() {
                function a(a, b) {
                    g[b] = "";
                    g[b] = a;
                    return !! g[b]
                }
                var c = {
                        "linear-gradient": {
                            property: "backgroundImage",
                            params: "red, teal"
                        },
                        calc: {
                            property: "width",
                            params: "1px + 5%"
                        },
                        element: {
                            property: "backgroundImage",
                            params: "#foo"
                        }
                    },
                    d = {
                        initial: "color",
                        "zoom-in": "cursor",
                        "zoom-out": "cursor",
                        box: "display",
                        flexbox: "display",
                        "inline-flexbox": "display"
                    };
                c["repeating-linear-gradient"] = c["repeating-radial-gradient"] = c["radial-gradient"] = c["linear-gradient"];
                b.functions = [];
                b.keywords = [];
                var g = document.createElement("div").style,
                    e;
                for (e in c) {
                    var h = c[e],
                        f = h.property,
                        h = e + "(" + h.params + ")"; ! a(h, f) && a(b.prefix + h, f) && b.functions.push(e)
                }
                for (var i in d) f = d[i],
                !a(i, f) && a(b.prefix + i, f) && b.keywords.push(i)
            })(); (function() {
                function a(a) {
                    g.textContent = a + "{}";
                    return !! g.sheet.cssRules.length
                }
                var c = {
                        ":read-only": null,
                        ":read-write": null,
                        ":any-link": null,
                        "::selection": null
                    },
                    d = {
                        keyframes: "name",
                        viewport: null,
                        document: 'regexp(".")'
                    };
                b.selectors = [];
                b.atrules = [];
                var g = f.appendChild(document.createElement("style")),
                    e;
                for (e in c) {
                    var h = e + (c[e] ? "(" + c[e] + ")": ""); ! a(h) && a(b.prefixSelector(h)) && b.selectors.push(e)
                }
                for (var j in d) h = j + " " + (d[j] || ""),
                !a("@" + h) && a("@" + b.prefix + h) && b.atrules.push(j);
                f.removeChild(g)
            })();
            b.valueProperties = ["transition", "transition-property"];
            f.className += " " + b.prefix;
            StyleFix.register(b.prefixCSS)
        }
    })(document.documentElement);
</script>


