import React, { useState, useEffect } from 'react'
import UscitaService from '../Services/UscitaService';

const Home = () => {

  const [loading, setLoading] = useState(true);
  const [uscite, setUscite] = useState(null);

  useEffect(() => {
    const fetchUscite = async () => {
      setLoading(true);
      try {
        const response = await UscitaService.calendarioUscite();
        setUscite(response.data.message);
      } catch (error) {
        console.log(error);
      }
      setLoading = false;
    }

  }, []);
  

  return (
    <>
    <div className='container mx-auto my-8 shadow border-b'>
      <div>
        Descrizione
      </div>
      <div>
        Destinazione
      </div>

      <div className='flex'>
        <table className='max-w-xs'>
          <thead>
            <tr>
              <th className='text-left uppercase tracking-wider py-3 px-6'>
                Nome
              </th>
              <th className='text-left uppercase tracking-wider py-3 px-6'>
                Cognome
              </th>
              <th className='text-left uppercase tracking-wider py-3 px-6'>
                Azioni
              </th>
            </tr>
          </thead>
          <tbody className='bg-white'>
            <tr>
              <td className='px-6'>Simone</td>
              <td className='px-6'>D'Orta</td>
              <button
                //onClick={salvaUtente}
                type="submit"
                className="w-full text-center px-6 rounded bg-green-400 text-white hover:bg-green-700 focus:outline-none my-1"
                >Promuovi</button>
            </tr>
          </tbody>
        </table>

      </div>
    </div>
    </>
  );
};

export default Home;