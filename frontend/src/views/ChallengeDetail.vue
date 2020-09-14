<template>
    <div>
        <v-btn
                color="pink"
                dark
                top
                left
                style="margin : 2%;"
        >
            <v-icon dark left>arrow_back</v-icon>메인으로
        </v-btn>
        <div style="margin-left: 20%; margin-top: 3%;">
            <v-card style="width:70%;" >
                <v-img
                src="/lego.ico"
                height="4vh"
                width="4vh"
                style="float:left; margin:2%;"
                ></v-img>
                <p style="font-size:4vh; font-weight: bold; margin-left:3%; padding-top:1%;" >{{title}}</p>
                <div>
                    <span style="font-size:2.5vh; font-weight: bold; margin-left: 2%; margin-right: 3%;">{{ startDate }}</span>  ~   <span style="font-size:2.5vh; font-weight: bold; margin-left: 3%;">{{ endDate }} </span> <span style="color:#ff5555; margin-left: 1%;">({{ periods }})</span>
                </div>
            </v-card>
            <v-card style="width:70%; padding: 1% 2%; margin-top: 3%;">
                <p style="font-size:2.5vh; margin-top: 2%;">배팅 금액 : <span style="font-size:3vh; font-weight: bold"> {{ fee }}원</span></p>
                <p style="font-size:2.5vh; margin-top: 2%;">분배 방식 : <span style="font-size:3vh; font-weight: bold"> {{ divide }}</span></p>
                <p style="font-size:2.5vh; margin-top: 2%;">인증 조건 : <span style="font-size:2.5vh; font-weight: bold"> {{ certificationCondition }}</span></p>
            </v-card>
            <v-card style="width:70%; padding: 1% 2%; margin-top: 3%;">
                <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold;">현재 참여 인원 : {{users.length}}명</p>
                <p style="font-size:2.5vh; margin-top: 2%;  font-weight: bold;">현재 까지 {{gather}}원이 모였습니다.</p>

            </v-card>
            <v-card style="width:70%; padding: 1% 2%; margin-top: 3%;">

              <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;" v-if="challengeState=='before'">마감까지 {{remain}}</p>
              <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;" v-if="challengeState=='doing'">본 챌린지는 진행중입니다. {{remain}}</p>
              <p style="font-size:2.5vh; margin-top: 2%; color:#ff5555; font-weight: bold;" v-if="challengeState=='done'">본 챌린지는 마감되었습니다. {{remain}}</p>
            </v-card>
            <div style="width:70%; padding: 1% 2%; margin-top: 3%; text-align: center;">
                <v-btn color="error" dark large style="margin: 2% 0; width:50%; height: 8vh; font-size:3vh; font-weight: bold;">참여하기</v-btn>

            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "challengeDetail",
        props:{
            cid:{
                type:Number
            }
        },
        data() {
            return {
                title:'',
                startDate:'',
                endDate: '',
                expireDate: '',
                fee : 0,
                isRandom: false,
                divide:'',
                certificationCondition: '',
                users: [],
                challengeState:'before',
                periods:'0',
                expire:'',
                remain:'',
                gather:0,

            }
        },
        mounted() {
            /*
            TODO : 추후 URL 수정 필요
            */
            axios.get('/mock/challenge'+this.cid+'.json',{
                params:{
                    id : Number(this.cid),

                }
            })
            .then((res)=>{
                this.title=res.data.name
                this.startDate=res.data.startDate.substr(2,8)
                this.endDate=res.data.endDate.substr(2,8)
                this.expireDate=res.data.expireDate.substr(2,8)
                this.expire=res.data.expireDate.substr(0,10);
                this.fee=res.data.fee
                this.isRandom=res.data.isRandom
                this.users=res.data.users
                this.certificationCondition=res.data.certificationCondition
                this.startDate=this.startDate.replace(/-/g,'/')
                this.endDate=this.endDate.replace(/-/g,'/')
                this.expireDate=this.expireDate.replace(/-/g,'/')
                let today=new Date().toISOString().substr(0,10)
                if(this.isRandom){
                    this.divide='랜덤 차등 분배'
                }else{
                    this.divide='균등 분배'
                }
                if(res.data.expireDate>=today){
                    this.challengeState='before'
                }
                else if(res.data.expireDate<today&&res.data.endDate>today){
                    this.challengeState='doing'
                }
                else{
                    this.challengeState='done'
                }
                this.gather=this.fee*this.users.length;
                let len = String(this.gather).length;
                let comma=len%3
                let tempstr=String(this.gather).substr(0,comma)
                while(comma<len){
                    tempstr+=','
                    tempstr+=String(this.gather).substr(comma,3)
                    comma+=3
                }
                this.gather=tempstr

                let from=new Date(res.data.startDate)
                let to = new Date(res.data.endDate)
                let differ= (to-from)/(24*60*60*1000)
                if(differ>=7){
                    if((differ%7)==0){
                        differ=Math.floor((differ/7))+'주'
                    }
                    else{
                        differ=Math.floor((differ/7))+'주 '+(differ%7)+'일'
                    }
                }else{
                    differ=differ+'일'
                }
                this.checkdate=true;
                this.periods=differ
            })
            .catch(()=>{
                /*
                TODO : 에러 페이지로 이동
                */
                this.$router.push()
            })




            setInterval(()=>{
                this.remainTime()
            },1000); // 타이머 1초간격으로 수행

        },
        methods:{
            remainTime() {
                let now = new Date()
                let exp = new Date(this.expire)
                if(now>exp){
                    return;
                }else{
                    let _sec=1000
                    let _min=60*_sec
                    let _hour=60*_min
                    let _day=24*_hour

                    let differ = exp-now
                    let day=Math.floor(differ/_day)
                    let hour=Math.floor((differ%_day)/_hour)
                    let min=Math.floor((differ%_hour)/_min)
                    let sec=Math.floor((differ%_min)/_sec)
                    this.remain=''
                    if(day>0){
                        this.remain+=day+'일 '
                    }
                    if(hour>0){
                        this.remain+=hour+'시간 '
                    }
                    if(min>0){
                        this.remain+=min+'분 '
                    }
                    if(sec>0){
                        this.remain+=sec+'초 '
                    }
                    if(day==0&&hour==0&&min==0&&sec==0){
                        this.remain='마감 되었습니다.'
                    }else{
                        this.remain+='남았습니다.'
                    }


                }
            },


        }

    }
</script>

<style scoped>

</style>