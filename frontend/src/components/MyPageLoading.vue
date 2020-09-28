<template>
  <div class="loan-funded">
    <img src="https://s3.amazonaws.com/f.cl.ly/items/2e223206231B0b342m2L/wallet-back.png"
         width="79"
         height="auto"
         class="wallet-back"/>

    <img src="https://s3.amazonaws.com/f.cl.ly/items/2T252w3K2K0u053V3y1U/money.png"
         width="49"
         height="auto"
         class="money-1"/>

    <img src="https://s3.amazonaws.com/f.cl.ly/items/2T252w3K2K0u053V3y1U/money.png"
         width="49"
         height="auto"
         class="money-2"/>

    <img src="https://s3.amazonaws.com/f.cl.ly/items/2T252w3K2K0u053V3y1U/money.png"
         width="49"
         height="auto"
         class="money-3"/>

    <img src="https://s3.amazonaws.com/f.cl.ly/items/3h210u3209263Q0f1y3H/wallet-front.png"
         width="79"
         height="auto"
         class="wallet-front"/>
  </div>
</template>

<style>
.loan-funded {
  height: 160px;
  width: 81px;
  position: relative;
  overflow: hidden;
  margin: 100px auto;

}

.wallet-front,
.wallet-back {
  position: absolute;
  bottom: -1px;
  left: 0;
}

@keyframes fall1 {
  0% {
    opacity: 0;
    top: -20px;
  }
  10% {
    opacity: 0;
  }
  30% {
    opacity: 1;
  }
  100% {
    opacity: 1;
    top: 135px;
    transform: rotate(70deg);
  }
}

.money-1 {
  position: absolute;
  top: -40px;
  left: 20px;

  animation: fall1 2.5s infinite;
}

.money-2 {
  position: absolute;
  top: -40px;
  left: 20px;

  animation: fall1 2.5s infinite;
  animation-delay: 0.75s;
  animation-timing-function: cubic-bezier(1.5, 0.5, 0.9, 1.5);
}

.money-3 {
  position: absolute;
  top: -40px;
  left: 20px;

  animation: fall1 2.5s infinite;
  animation-delay: 1.5s;
  animation-timing-function: cubic-bezier(1.5, 2.5, 0.3, 1.5);
}

</style>