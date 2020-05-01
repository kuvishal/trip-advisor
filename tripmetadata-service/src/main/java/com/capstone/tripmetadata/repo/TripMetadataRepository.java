/**
 * 
 */
package com.capstone.tripmetadata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.capstone.tripmetadata.model.TripMetaData;

/**
 * @author kvishal
 *
 */
public interface TripMetadataRepository extends JpaRepository<TripMetaData, Long>,QueryByExampleExecutor<TripMetaData> {

}
