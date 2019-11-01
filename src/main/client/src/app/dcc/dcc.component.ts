import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DccCommand } from './dcc-command'
import { DccStatus } from './dcc-status';
import { DccLogItem } from './dcc-log-item';

@Component({
    selector: 'app-dcc',
    templateUrl: './dcc.component.html',
    styleUrls: ['./dcc.component.css']
})
export class DccComponent implements OnInit {

    dccCommand: DccCommand;

    dccStatus: DccStatus;

    logItems: DccLogItem[] = null;
    logTimestamp: number = 0;

    /**
     * Custom constructor.
     */
    constructor(
        private http: HttpClient
    ) {
        this.dccCommand = new DccCommand()

    }

    /**
     * Is called after class has been initialized.
     */
    ngOnInit() {

        // reload logs
        this.autoReloadLogs();

        // reload status
        // this.reloadStatus();
    }

    sendCommand() {
        this.executeCommand(this.dccCommand);
    }

    /**
     * Execute a DCC Command
     */
    executeCommand(dccCommand: DccCommand) {

        // post
        this.http.post('/dcc', dccCommand)
            .subscribe(
                data => this.reloadLogs(),
                error => console.log("Error: " + error)
            )
    }

    /**
     * Switch on DCC Controller.
     */
    switchOn() {
        let cmd: DccCommand = new DccCommand();
        cmd.command = "<1>";

        this.executeCommand(cmd);
    }

    /**
     * Switch off DCC Controller.
     */
    switchOff() {
        let cmd: DccCommand = new DccCommand();
        cmd.command = "<0>";

        this.executeCommand(cmd);
    }

    /**
     * Auto updater for logs.
     */
    autoReloadLogs() {
        this.reloadLogs();
        
        setTimeout(() => {
            this.autoReloadLogs();
        }, 2500);
    }

    /**
     * Update logs.
     */
    reloadLogs() {
        this.http.get<DccLogItem[]>('/dcc/logs/' + this.logTimestamp)
            .subscribe(
                data => {

                    // save logs
                    if (this.logItems == null) {
                        this.logItems = data;
                    } else {
                        this.logItems = data.concat(this.logItems);
                    }

                    // keep latest timestamp
                    if (data.length > 0) {
                        this.logTimestamp = data[0].timestamp;
                    }

                    // shorten messages list
                    if (this.logItems.length > 6000) {
                        this.logItems = this.logItems.slice(0, 6000);
                    }
                },
                error => console.log("Error: " + error)
            )
    }

    /**
     * Automated status update.
     */
    reloadStatus() {
        setTimeout(() => {
            this.http.get<DccStatus>('/dcc/status')
                .subscribe(
                    data => this.dccStatus = data,
                    error => console.log("Error: " + error)
                )

            this.reloadStatus();
        }, 2000);
    }
}
