<template>
  <div>
    <v-btn @click="send">송금</v-btn>
  </div>
</template>

<script>
const Web3 = require('web3')
// const web3 = new Web3(new Web3.providers.HttpProvider('http://j3a102.p.ssafy.io:8545'))
const web3 = new Web3(new Web3.providers.HttpProvider('https://ropsten.infura.io/v3/132d48f7fad8474db95aa5359cec4524'))
const abi = [
  {
    "anonymous": false,
    "inputs": [
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_value",
        "type": "uint256"
      },
      {
        "indexed": false,
        "internalType": "uint256",
        "name": "_balance",
        "type": "uint256"
      }
    ],
    "name": "Sended",
    "type": "event"
  },
  {
    "inputs": [],
    "name": "sending",
    "outputs": [],
    "stateMutability": "payable",
    "type": "function"
  },
  {
    "inputs": [
      {
        "internalType": "address payable",
        "name": "_friend",
        "type": "address"
      }
    ],
    "name": "set",
    "outputs": [],
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "inputs": [],
    "name": "transfer",
    "outputs": [],
    "stateMutability": "payable",
    "type": "function"
  }
]
const contract = new web3.eth.Contract(abi, '0x62BdC0eAF8855067516998Cb5f0bDb81588a70B5')

export default {
  name: "tranferTest",
  methods: {
    send() {
      const res = contract.methods.sending().
      console.log(res)

      contract.methods.set('0x51465Bd3F63F275Ed5fde5918D31C2A51650b964')
      const tres = contract.methods.transfer()
      console.log(tres)
    }
  }
}
</script>

<style scoped>

</style>