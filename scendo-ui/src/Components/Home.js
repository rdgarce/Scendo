import e from 'cors';
import React, { useState, useEffect } from 'react'
import UscitaService from '../Services/UscitaService';
import ContainerUscita from './ContainerUscita';

const Home = () => {

  const [loading, setLoading] = useState(true);
  const [uscite, setUscite] = useState([]);

  useEffect(() => {
    const fetchUsciteId = async () => {
      setLoading(true);
      try {
        const response = await UscitaService.calendarioUscite();
        const listaUscite = [];
        if (response.message){
            for (let i = 0; i < response.message.length; i++) {
                const element = await UscitaService.infoUscita(response.message[i], true);
                listaUscite.push(element);  
                console.log("element", element);
            }
        }
        setUscite(listaUscite); 
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchUsciteId();
  }, []);

  return (
    <>
    {!loading &&(
        <div>
        {uscite.map((uscita) => (
            <ContainerUscita 
            uscita={uscita} 
            key={uscita.message.idUscita}>            
            </ContainerUscita>
            
        ))}
        </div>
    )}
    </>
  )
}

export default Home