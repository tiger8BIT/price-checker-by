let notFoundTextHTML = '<div class="center"><span class="label">Not found</span></div>';
let chartContainerContentHTML = '<canvas class="chart"></canvas>' +
    '<div class="button-container">' +
    '<button class = "btn-date year" type="button">Year</button>' +
    '<button class = "btn-date mounth" type="button" >Mounth</button>' +
    '<button class = "btn-date week" type="button" >Week</button>' +
    '</div>' +
    '<img id="subscribe">' +
    '<div id="subscribe-bar" class="rem"></div>';
let subscriptionFormHtml =
    '    <input id="email" class="text input" type="email" placeholder="Email" required>\n' +
    '    <input id="sum" class="text input" type="number" step="1" min="1" placeholder="Price lower" required>\n' +
    '    <div class="subscription-form-buttons">\n' +
    '        <input id="cancel" type="button" value="Cancel">\n' +
    '        <div class="spacer"></div>\n' +
    '        <input id="ok" type="submit" value="Ok">\n' +
    '    </div>';
let selectedDate = "mounth";
let chartContainer;
let contentContainer;
let subscriptionForm;
let subscribeElement;
document.addEventListener('DOMContentLoaded', function () {
    chartContainer = document.getElementsByClassName("chart-container")[0];
    subscriptionForm = document.getElementsByClassName("subscription-form")[0];
    contentContainer = document.getElementById('content');
    search(getLastMounth());
});
function search(startDate) {
    progressBar();
    serverRequest(startDate);
    showResult();
}
function selectButton(button, buttons) {
    for (let item of buttons) {
        item.style.backgroundColor = Object.is(item, button) ? "silver" : "white";
    }
    switch(button.className){
        case 'btn-date year':
            selectedDate = "year";
            search(getLastYear());
            break;
        case 'btn-date mounth':
            selectedDate = "mounth";
            search(getLastMounth());
            break;
        case 'btn-date week':
            selectedDate = "week";
            search(getLastWeek());
            break;
        default:
            break;
    }
}
function getLastMounth(){
  let d = new Date();
  d.setMonth(d.getMonth() - 1);
  d.setHours(0, 0, 0);
  d.setMilliseconds(0);
  return d;
}
function getLastWeek(){
  let d = new Date();
  d.setDate(d.getDate() - 5);
  d.setHours(0, 0, 0);
  d.setMilliseconds(0);
  return d;
}
function subscribe(button){
    if(button.issubscribe === 'true') {
        button.title='Subscribe';
        button.issubscribe = 'false';
        button.src = 'data:image/png;base64,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';
    } else {
        var email = $('#email').val();
        var sum = $('#sum').val();
        localStorage.setItem('PRICE_CHECKER_BY_EMAIL', email);
        subscribeRequest(button, email, sum);
    }
}
function subscribeRequest(button, email, reductionAmount) {
    chrome.storage.sync.get('url', function(data) {
        var url = data.url;
        let outputObj = {
            "email": email,
            "reductionAmount": reductionAmount,
            "url": url
        };
        $.ajax(
            {
                url: "http://localhost:8080/subscription",
                type: "POST",
                contentType : 'application/json',
                data : JSON.stringify(outputObj),
                success: function (result) {
                    removeItems();
                    button.title='Subscribed';
                    $('.subscription-form').animate({height: 0}, "slow", function() {
                        subscriptionForm.innerHTML = '';
                    });
                    button.issubscribe = 'true';
                    button.src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAEu0lEQVR4Xu2bd4gVVxTGXXuJYgNBWZRYiEhYFEVEcbNGDSr2iCUxiUTsWFDBgOCqREVFAnYFxYKiJtjFNEMSjUIsKCqK+If4h4oNxZ5Yvp/MyPj2lZl5M3fe7uyBj52Zd+8p39yZO/ees3kVYi55MY+/QjkB5SMg5gz4eQQaibMioZ3QUqgv1BCeCjeFS8IJ4U/hSUD81pKeT4SOwkcCPtg27+r4inBK+EO47cWmWwIqSekQYaxQKLjp90zt9gorhKNenHK07arjSUI/oZoLHa8sEtbp748C52nFTSB9pWGZ0CKTsjS//6bfpgoXXOr4WO1+ELq5bJ+s2WXL5uF0OtIRUFsd1wgjsnDC2fU/ncwWlgivU+jEn1nCPKFyQHY3Sc9E4XEyfakIaKLGPwttAnLCqWanTkYKLxJ0M8S3Cp+HYPOsdPYSbiTqTkYAwfPMNgvBEVvlQR0MFBgVSFVhj+VkWGavSnFn4ZbTQCIBvG2PCzyDYct6GRhjGdmgv6PCNij9py0SeEG/lUQCNuraNwYcsU18pQNmGOyakrUyNC4ZAd118VdTXlh27ls3oa5hu0zlfzlHACOBF4WJoW841qTmTupqBycBfXRyIBc8M+hDT0a8/Q7YrZMBBo3ngqkdcmIYBNQUeBaZiuIkrFPqQUAUL79cIboQAmYIfJ7GUaZAwGrh3bwYMxaWQ8B2XgYxC9wOdwsE/CQMiikBOyBgs8DqLI6yAQIWCqzB4yjzIYC7zyiIowyHgA8F1spxlHz7U/iiom8dMwbOKd4Cm4DvdLIgZgTMVLxLbQIa6OSawI5QHOSRgmwq3HPuCM3XBXZt4yDFCnIugToJYFXIc9G8jDNAFqlAIJNVYk+Q1BNbRWV1acxWfBfhX/smJ9sW/1o/sknpJmtUmgYLyRg2Yck9vJNUQU5QC3J6ZYUEgicmMl3vSboAv1VLkowVS9NtTuLrS10jFlJkJSTTHR6uHnwmB5WnM80lmacvhF2pDGcigH79BTYQ3aSnTQeYzh7ZH/KMpOFSihsC6NxDIHfHVFkahEwwNQVHMjnrlgD0MH3AZp1MSiP+/YHs9xb+ceOHFwLQ114gbU5ZTC7KHTlFwuOMW+e8EoBe0mfkEKnTySUh988WPytb1+KHAJS3Eih7yXdtKdyGLOQ+FTzva/glgHBYTf0uRL124Nue4K/74TgbArDX2BoJUW2mnJd9ZijK83xJtgRgtKHwi9DWlwf+O1EX+JlAnaBvCYIAjFPgcEjo5NsTbx2PqTlT3UNv3Uq2DooANH8g7BOKsnUqQ39evnydBlKFGiQB+F1dINPE3QlD9kspFavPg1IeNAH4VUXYJgRd78d65Evh/6CCR08YBKCXyi9K39iACELYoBktZKz99WosLAJsclfqYLxXpxLaszEzWUhVXpuV+jAJsB2j+IIiDD+ySJ3IWYQmJgjA+TlCscco2KL/3mMfz81NEYBj04WlLj2cpnaUy4cuJgkgGEpxVgmp7PKSow11xEbENAEERTqetzozhVOY3tiSZwo1JlEQQHCDBWqT+GZASFgMFdh2MypREUCQ/DsM7wWG/WLhb6ORW8aiJCCKeEvYLCcgJ25DhE6Uj4AIyc8J028A5ICoYrSa/BsAAAAASUVORK5CYII=';
                },
                error: function (result) {
                    console.log('what>>>');
                    notFound();
                    console.log(result);
                }
            });
    });
}
function getLastYear(){
  let d = new Date();
  d.setYear(d.getYear() - 1);
  d.setHours(0, 0, 0);
  d.setMilliseconds(0);
  return d;
}
function removeItems(){
  let rem = document.querySelectorAll(".rem");
  rem.forEach(r => {
      r.innerHTML = "";
  });
}
function putChartContainerContent(content){
  chartContainer.innerHTML = content;
}
function notFound(){
    removeItems();
  putChartContainerContent(notFoundTextHTML);
}
function fillChartContainer(){
  putChartContainerContent(chartContainerContentHTML);
  var dateBtn = document.getElementsByClassName("btn-date");
  selectedDateBtn = document.getElementsByClassName(selectedDate)[0];
  selectedDateBtn.style.backgroundColor = "silver";
  console.log(dateBtn);
  for (let item of dateBtn) {
    item.onclick = function(e){
      selectButton(e.target, dateBtn);
    }
  }
}
function subscribeActions() {
    subscribeElement = document.getElementById('subscribe');
    subscribeElement.issubscribe = 'true';
    subscribe(subscribeElement);
    subscribeElement.addEventListener('click', (likeButton => {
        //chartContainer.style = 'width: 0';
        subscriptionForm.innerHTML = subscriptionFormHtml;
        $('.subscription-form').animate({height: '100px'}, "slow", function() {
        });
        document.getElementById('cancel').addEventListener('click', (ev => {
            $('.subscription-form').animate({height: 0}, "slow", function() {
                subscriptionForm.innerHTML = '';
            });
        }));
        $( ".subscription-form" ).submit(function( event ) {
            event.preventDefault();
            subscribeProgressBar();
            subscribe(likeButton.target);
        });
    }));
}
function drawChart(labels, values) {
    removeItems();
    fillChartContainer();
    subscribeActions();
    let ctx = document.getElementsByClassName("chart");
    let myLineChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels, // Our labels
            datasets: [{
                label: 'Price', // Name the series
                data: values, // Our values
                backgroundColor: [ // Specify custom colors
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [ // Add custom color borders
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 1 // Specify bar border width
            }]
        },
        options:{
            scales:{
                xAxes: [{
                    display: false //this will remove all the x-axis grid lines
                }]
            },
            layout: {
                padding: {
                    left: 15,
                    right: 25,
                    top: 15,
                    bottom: 20
                }
            }
        }
    });
}
function serverRequest(startDate){
    chrome.storage.sync.get('url', function(data) {
        var url = data.url;
        let outputObj = {url: url, startDate: startDate};
        $.ajax(
            {
                url: "http://localhost:8080/search",
                type: "POST",
                contentType : 'application/json',
                dataType: "json",
                data : JSON.stringify(outputObj),
                success: function (json) {
                    let labels = json.map(function (e) {
                        return new Date(e.date).toISOString().split('T')[0];
                    });
                    let values = json.map(function (e) {
                        return (e.price);
                    });
                    drawChart(labels, values);
                },
                error: function (result) {
                    notFound();
                    console.log(result);
                }
            });
    });
}
function showResult(){
    let chartContainer = $(".chart-container");
    chartContainer.css('display','block');
    chartContainer.animate({opacity: 1}, "slow", function() {
    })
}
function progressBar(){
    let bar = new RadialProgress(document.getElementById("bar"), {indeterminate:true,colorBg:"rgba(0,0,0,0)",colorFg:"silver",thick:5, height: "40px", width: "40px"});
}
function subscribeProgressBar(){
    let bar = new RadialProgress(document.getElementById("subscribe-bar"), {indeterminate:true,colorBg:"rgba(0,0,0,0)",colorFg:"silver",thick:5, height: "40px", width: "40px"});
}
window.rp_requestAnimationFrame=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.msRequestAnimationFrame||(function(callback,element){setTimeout(callback,1000/60);});
function RadialProgress(container,cfg){
    container.innerHTML="";
    var nc=document.createElement("div");
    nc.style.width=cfg.height; nc.style.height=cfg.width;
    nc.style.position="relative";
    container.appendChild(nc);
    container=nc;
    if(!cfg) cfg={};
    this.colorBg=cfg.colorBg==undefined?"#404040":cfg.colorBg;
    this.colorFg=cfg.colorFg==undefined?"#007FFF":cfg.colorFg;
    this.colorText=cfg.colorText==undefined?"#FFFFFF":cfg.colorText;
    this.indeterminate=cfg.indeterminate==undefined?false:cfg.indeterminate;
    this.round=cfg.round==undefined?false:cfg.round;
    this.thick=cfg.thick==undefined?2:cfg.thick;
    this.progress=cfg.progress==undefined?0:cfg.progress;
    this.noAnimations=cfg.noAnimations==undefined?0:cfg.noAnimations;
    this.fixedTextSize=cfg.fixedTextSize==undefined?false:cfg.fixedTextSize;
    this.animationSpeed=cfg.animationSpeed==undefined?1:cfg.animationSpeed>0?cfg.animationSpeed:1;
    this.noPercentage=cfg.noPercentage==undefined?false:cfg.noPercentage;
    this.spin=cfg.spin==undefined?false:cfg.spin;
    if(cfg.noInitAnimation) this.aniP=this.progress; else this.aniP=0;
    var c=document.createElement("canvas");
    c.style.position="absolute";c.style.top="0";c.style.left="0";c.style.width="100%";c.style.height="100%";c.className="rp_canvas";
    container.appendChild(c);
    this.canvas=c;
    var tcc=document.createElement("div");
    tcc.style.position="absolute";tcc.style.display="table";tcc.style.width="100%";tcc.style.height="100%";
    var tc=document.createElement("div");
    tc.style.display="table-cell";tc.style.verticalAlign="middle";
    var t=document.createElement("div");
    t.style.color=this.colorText;t.style.textAlign="center";t.style.overflow="visible";t.style.whiteSpace="nowrap";t.className="rp_text";
    tc.appendChild(t);
    tcc.appendChild(tc);
    container.appendChild(tcc);
    this.text=t;
    this.prevW=0; this.prevH=0; this.prevP=0; this.indetA=0; this.indetB=0.2; this.rot=0;
    this.draw=function(f){
        if(!(f==true))rp_requestAnimationFrame(this.draw);
        var c=this.canvas;
        var dp=window.devicePixelRatio||1;
        c.width=c.clientWidth*dp; c.height=c.clientHeight*dp;
        if(!(f==true)&&!this.spin&&!this.indeterminate&&(Math.abs(this.prevP-this.progress)<1&&this.prevW==c.width&&this.prevH==c.height)) return;
        var centerX=c.width/2, centerY=c.height/2, bw=(c.clientWidth/100.0), radius=c.height/2-(this.thick*bw*dp)/2, bw=(c.clientWidth/100.0);
        this.text.style.fontSize=(this.fixedTextSize?(c.clientWidth*this.fixedTextSize):(c.clientWidth*0.26-this.thick))+"px";
        if(this.noAnimations) this.aniP=this.progress; else{var aniF=Math.pow(0.93,this.animationSpeed);this.aniP=this.aniP*aniF+this.progress*(1-aniF);}
        c=c.getContext("2d");
        c.beginPath();
        c.strokeStyle=this.colorBg;
        c.lineWidth=this.thick*bw*dp;
        c.arc(centerX,centerY,radius,-Math.PI/2, 2*Math.PI);
        c.stroke();
        c.beginPath();
        c.strokeStyle=this.colorFg;
        c.lineWidth=this.thick*bw*dp;
        if(this.round) c.lineCap="round";
        if(this.indeterminate){
            this.indetA=(this.indetA+0.07*this.animationSpeed)%(2*Math.PI);this.indetB=(this.indetB+0.14*this.animationSpeed)%(2*Math.PI);
            c.arc(centerX,centerY,radius,this.indetA, this.indetB);
            if(!this.noPercentage)this.text.innerHTML="";
        }else{
            if(this.spin&&!this.noAnimations)this.rot=(this.rot+0.07*this.animationSpeed)%(2*Math.PI)
            c.arc(centerX,centerY,radius,this.rot-Math.PI/2, this.rot+this.aniP*(2*Math.PI)-Math.PI/2);
            if(!this.noPercentage)this.text.innerHTML=Math.round(100*this.aniP)+" %";
        }
        c.stroke();
        this.prevW=c.width; this.prevH=c.height; this.prevP=this.aniP;
    }.bind(this);
    this.draw();
}
