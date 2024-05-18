import React from "react";
import ModalWindow from "../../../components/modal/ModalWindow";
import Button, {ButtonSize, ButtonType} from "../../../components/button/Button";
import {userApi} from "../../../services/api";
import {QrReader} from "react-qr-reader";
import styles from "../../scan-page/ScanPage.module.scss";


export const ModalScanCode = (props) => {
    if (!props.show)
        return null;

    const handleDeleteOrder = async () => {
        try {
            const orderId = props.orderId;
            await userApi.deleteOrder(orderId);
            window.location.reload();
        } catch (error) {
            console.log('error - delete order')
        }
    }

    const stopScan = () => {
        props.onClose(true);
    }

    const handleScan = (code) => {
        props.scanedCode(code);
    }


    function getContent() {
        return (
            <div className={'modal-window-body'}>

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
        )
    }

    return (
        <ModalWindow show={props.show}
                     title={"Scan code"}
                     content={getContent()}/>
    )
}
