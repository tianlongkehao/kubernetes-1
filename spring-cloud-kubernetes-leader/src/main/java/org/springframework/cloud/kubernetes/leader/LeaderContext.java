/*
 * Copyright (C) 2018 to the original authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.kubernetes.leader;

import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.Context;

/**
 * @author <a href="mailto:gytis@redhat.com">Gytis Trikleris</a>
 */
public class LeaderContext implements Context {

	private final Candidate candidate;

	private final LeadershipController leadershipController;

	public LeaderContext(Candidate candidate, LeadershipController leadershipController) {
		this.candidate = candidate;
		this.leadershipController = leadershipController;
	}

	@Override
	public boolean isLeader() {
		Leader leader = leadershipController.getLeader(candidate.getRole());
		if (leader == null) {
			return false;
		}

		return candidate.getId().equals(leader.getId());
	}

	@Override
	public void yield() {
		leadershipController.revoke(candidate);
	}
}
