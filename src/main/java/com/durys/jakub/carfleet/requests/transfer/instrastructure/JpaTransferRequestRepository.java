package com.durys.jakub.carfleet.requests.transfer.instrastructure;

import com.durys.jakub.carfleet.requests.transfer.domain.TransferRequest;
import com.durys.jakub.carfleet.sharedkernel.requests.RequestId;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaTransferRequestRepository extends JpaRepository<TransferRequest, RequestId> {
}
