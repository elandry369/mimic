package com.cloakdroid.engine

import com.cloakdroid.data.local.FingerprintConfig

object ScriptInjector {
 /** Script content is deterministic per profile and is intended for document-start registration by Gecko integrations. */
 fun documentStart(config: FingerprintConfig): String = """
 (() => { const c=${config.latitude}, d=${config.longitude}, a=${config.accuracy}, s=${config.noiseSeed};
 const jitter=()=>((Math.sin(s*999)+1)/2-.5)*.01;
 const pos=()=>({coords:{latitude:c+jitter(),longitude:d+jitter(),accuracy:a,altitude:null,altitudeAccuracy:null,heading:null,speed:null},timestamp:Date.now()});
 const geo={getCurrentPosition:(ok)=>ok(pos()),watchPosition:(ok)=>{ok(pos());return 1;},clearWatch:()=>{}};
 try{Object.defineProperty(navigator,'geolocation',{value:geo});Object.defineProperty(navigator,'hardwareConcurrency',{get:()=>${config.hardwareConcurrency}});Object.defineProperty(navigator,'deviceMemory',{get:()=>${config.deviceMemory}});}catch(e){}
 const q=navigator.permissions&&navigator.permissions.query.bind(navigator.permissions); if(q) navigator.permissions.query=(p)=>p.name==='geolocation'?Promise.resolve({state:'granted'}):q(p);
 const n=((s%3)-1); const old=HTMLCanvasElement.prototype.toDataURL; HTMLCanvasElement.prototype.toDataURL=function(){return old.apply(this,arguments)};
 const gi=CanvasRenderingContext2D.prototype.getImageData; CanvasRenderingContext2D.prototype.getImageData=function(){const x=gi.apply(this,arguments);for(let i=0;i<x.data.length;i+=4)x.data[i]=Math.max(0,Math.min(255,x.data[i]+n));return x};
 const ac=AudioBuffer.prototype.getChannelData; AudioBuffer.prototype.getChannelData=function(){const x=ac.apply(this,arguments);for(let i=0;i<x.length;i+=128)x[i]+=((s%11)-5)*1e-7;return x};
 })();
 """.trimIndent()
}
