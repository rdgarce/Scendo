import React from "react";
import {
  Hero,
  Flex,
} from "react-landing-page";


const Scendo = () => {
  return (
    <>
    <Hero
      color="white"
      backgroundImage="https://www.vinhood.com/wp-content/uploads/2022/01/rec_cosa_non_fare_2.jpg"
      
      bg="black"
      bgOpacity={0.5}
    >
      <h1 className="mb-8 text-9xl text-center font-thin">Scendo</h1>
      <h2 className="mb-8 text-2xl text-center font-light">Una nuovo modo di organizzare le uscite con gli amici</h2>
      <Flex mt={3}>
      </Flex>
    </Hero>
    </>
  )
}

export default Scendo;