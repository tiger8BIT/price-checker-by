var regex;
var bindRules = () => {serverRequest((domains)=> {
    regex = domains.reduce((accumulator, currentValue) => {
        currentValue = currentValue.replace(".", "\\.");
        currentValue = currentValue.replace("/", "\\/");
        return (accumulator && accumulator + "|") + currentValue + "*";
    }, "");
    console.log(regex);
    var rules = [];
    domains.forEach((domain) => {
        var rule = {
            conditions: [
                new chrome.declarativeContent.PageStateMatcher({
                    pageUrl: { urlContains: domain },
                })
            ],
            // And shows the extension's page action.
            actions: [new chrome.declarativeContent.ShowPageAction()]
        };
        rules.push(rule);
    });
    chrome.declarativeContent.onPageChanged.removeRules(undefined, function() {
        chrome.declarativeContent.onPageChanged.addRules(rules);
    });
})};
chrome.windows.onCreated.addListener(function() {
    bindRules()
});
chrome.runtime.onInstalled.addListener(function() {
    bindRules()
});
function checkForValidUrl(url) {
    console.log(url);
    if (url.match(new RegExp(regex, 'gm'))) {
        chrome.storage.sync.set({url: url})
    }
    else {
        chrome.storage.sync.set({url: ""})
    }
}
chrome.tabs.onUpdated.addListener((tabId, changeInfo, tab)=>checkForValidUrl(tab.url));
chrome.tabs.onActivated.addListener((tab)=>chrome.tabs.getSelected(null, (tab) => checkForValidUrl( tab.url)));
function serverRequest(onSuccess){
    $.ajax(
        {
            url: "http://localhost:8080/domains/url",
            type: "GET",
            contentType : 'application/json',
            dataType: "json",
            success: function (domains) {
                onSuccess(domains);
            },
            error: function (result) {
                console.log(result);
            }
        });
}
/*chrome.tabs.onActivated.addListener(checkUrl);
function updateIcon() {
    chrome.browserAction.setIcon({path: 'icon' + 2 + '.png'});
};

function checkUrl() {
    chrome.tabs.getSelected(null,function(tab) {
        var url = tab.url;
        if (url.match(/https:\/\/www.wildberries.by/)) {
            chrome.browserAction.setIcon({path: 'icon' + 2 + '.png'});
            chrome.browserAction.setPopup({popup: "popup.html"});
        }
        else {
            chrome.browserAction.setIcon({path: 'icon' + 1 + '.png'});
            chrome.browserAction.setPopup({popup: ""});
        }
    })
}*/