var admin = require("firebase-admin");

var serviceAccount = require("./jpstagram-b99c5-firebase-adminsdk-dyjlm-c638b3418f.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

var firestore = admin.firestore()
firestore.collection("User").where("allow","==",true).get()
.then(result=>{

    var messages={
        notification:{
            title:"test",
            body:"test"
        },
        tokens:[]
    }
    result.forEach(item=>{

        messages.tokens.push(item.data().token)
    })

    console.log(messages)
    if(messages.tokens.length>0){
        var messaging=admin.messaging()
        messaging.sendMulticast(messages).then(result=>{
            console.log(result)
            process.exit()
        })
    }
})
