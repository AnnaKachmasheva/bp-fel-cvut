import React from 'react';
import {QrReader} from 'react-qr-reader';
import styles from './ScanPage.module.scss';
import Button, {ButtonSize, ButtonType} from "../../components/button/Button";
import {useNavigate} from "react-router-dom";
import {decodeData} from "../../utils/Common";

const ScanPage = () => {

    let navigate = useNavigate();

    const handleScan = data => {
        const response = decodeData(data);
        if (response.message) {
            console.log(response.message);
        }
    }

    const stopScan = err => {
        navigate(-1);
    }

    return (
        <div className={styles.scanContainer}>

            <QrReader
                className={styles.qrReader}
                constraints={{facingMode: 'environment'}}
                videoStyle={{height: '100%'}}
                onResult={(result, error) => {
                    if (!!result) {
                        handleScan(result?.text);
                    }
                    if (!!error) {
                        console.info(error);
                    }
                }}
            />

            <div className={styles.Button}>
                <Button type={ButtonType[4].type}
                        size={ButtonSize[1].size}
                        onClick={stopScan}
                        label={'Stop'}/>
            </div>

        </div>
    );
};

export default ScanPage;